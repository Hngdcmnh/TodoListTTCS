package com.example.todolist.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.TaskRepository
import com.example.todolist.model.Task
import com.example.todolist.model.TaskStatus
import com.example.todolist.utils.dateToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.util.*

class TaskViewModel(private var taskRepository: TaskRepository):ViewModel() {
    private var _listTask = taskRepository.getAllTask as LiveData<MutableList<Task>>
    val listTask: LiveData<MutableList<Task>>
        get() = _listTask

    private var _listPendingTask = MutableLiveData<MutableList<Task>>()
    val listPendingTask: LiveData<MutableList<Task>>
        get() = _listPendingTask

    private var _listDoneTask = MutableLiveData<MutableList<Task>>()
    val listDoneTask: LiveData<MutableList<Task>>
        get() = _listDoneTask

    private var _openAddTaskDialog = MutableLiveData<Boolean>()
    val openAddTaskDialog: LiveData<Boolean>
        get() = _openAddTaskDialog

    private var _listDeleteTask = MutableLiveData<MutableList<Task>>()
    val listDeleteTask: LiveData<MutableList<Task>>
        get() = _listDeleteTask

    init {
        _openAddTaskDialog.value = false
        updateListDoneTask()
    }

    fun updateListDoneTask() {
        val pendingTasks = mutableListOf<Task>()
        val doneTasks = mutableListOf<Task>()
        val deletedTasks = mutableListOf<Task>()
        _listTask.value?.let {
            for (task in it) {
                if (task.status == TaskStatus.DONE.toString()) {
                    doneTasks.add(task)
                } else if (task.status == TaskStatus.PENDING.toString()) {
                    pendingTasks.add(task)
                }
                else
                {
                    deletedTasks.add(task)
                }
            }
        }
        _listDeleteTask.postValue(deletedTasks)
        _listPendingTask.postValue(pendingTasks)
        _listDoneTask.postValue(doneTasks)
    }

    fun addPendingTasks(task: Task)
    {
        _listPendingTask.value?.add(task)

        viewModelScope.launch {
            taskRepository.addTask(task)
            _listDeleteTask.postValue(_listPendingTask.value)
        }
    }


    fun deletePendingTasks(task: Task)
    {
        _listPendingTask.value?.remove(task)
        task.status = TaskStatus.PENDING_DELETED.toString()
        _listDeleteTask.value?.add(task)
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun deleteDoneTasks(task: Task)
    {
        _listDoneTask.value?.remove(task)
        task.status = TaskStatus.DONE_DELETED.toString()
        _listDeleteTask.value?.add(task)
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun eventAddNewTask(){
        if(_openAddTaskDialog.value==false)
        {
            _openAddTaskDialog.value = true
        }
    }

    fun removeInDatabase(task: Task){
        val removeTask = _listDeleteTask.value?.find{ itemTask -> itemTask == task }
        _listDeleteTask.value?.remove(removeTask)
        _listDeleteTask.postValue(_listDeleteTask.value)

        viewModelScope.launch {
            taskRepository.deleteTask(task.id)
        }
    }

    fun rollBack(task: Task){
        val removeTask = _listDeleteTask.value?.find{ itemTask -> itemTask == task }
        if(removeTask?.status == TaskStatus.PENDING_DELETED.toString())
        {
            _listDeleteTask.value?.remove(removeTask)
            _listDeleteTask.postValue(_listDeleteTask.value)
            removeTask.status = TaskStatus.PENDING.toString()
            _listPendingTask.value?.add(removeTask)
            _listPendingTask.postValue(_listPendingTask.value)
        }
        else if(removeTask?.status == TaskStatus.DONE_DELETED.toString())
        {
            _listDeleteTask.value?.remove(removeTask)
            _listDeleteTask.postValue(_listDeleteTask.value)
            removeTask.status = TaskStatus.DONE.toString()
            _listDoneTask.value?.add(removeTask)
            _listDoneTask.postValue(_listDoneTask.value)
        }

        viewModelScope.launch {
            if (removeTask != null) {
                taskRepository.updateTask(removeTask)
            };
        }

    }

    fun dismissAddNewTaskDialog()
    {
        if(_openAddTaskDialog.value==true)
        {
            _openAddTaskDialog.value = false
        }
    }

    fun onClickCheckBox(taskClick: Task) {

        val taskPending = _listPendingTask.value?.find { task -> taskClick.title == task.title }
        taskPending?.status = TaskStatus.DONE.toString()
        Log.e(this.javaClass.simpleName,taskPending?.title.toString())

        val taskDone = _listDoneTask.value?.find { task -> taskClick.title == task.title }
        taskDone?.status = TaskStatus.PENDING.toString()

        if (taskPending != null) {
            _listDoneTask.value?.add(taskPending)
            _listDoneTask.postValue(_listDoneTask.value)

            _listPendingTask.value?.remove(taskPending)

            _listPendingTask.postValue(_listPendingTask.value)

            viewModelScope.launch {
                taskRepository.updateTask(taskPending);
            }
        }

        if(taskDone != null)
        {
            _listPendingTask.value?.add(taskDone)
            _listPendingTask.postValue(_listPendingTask.value)

            _listDoneTask.value?.remove(taskDone)
            _listDoneTask.postValue(_listDoneTask.value)

            viewModelScope.launch {
                taskRepository.updateTask(taskDone);
            }
        }
    }
}
