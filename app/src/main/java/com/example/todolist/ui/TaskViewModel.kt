package com.example.todolist.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.model.Task
import com.example.todolist.model.TaskStatus

import java.util.*

class TaskViewModel:ViewModel() {
    private var _listTask = MutableLiveData<MutableList<Task>>()
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
        val tasks = mutableListOf<Task>()
        for (i in 1..10) {
            val cal = Calendar.getInstance().time
            tasks.add(Task(i, "task $i", "description $i", cal, TaskStatus.PENDING))
        }
        _listTask.value = tasks
        updateListDoneTask()
    }

    fun updateListDoneTask() {
        val pendingTasks = mutableListOf<Task>()
        val doneTasks = mutableListOf<Task>()
        val deletedTasks = mutableListOf<Task>()
        _listTask.value?.let {
            for (task in it) {
                if (task.status == TaskStatus.DONE) {
                    doneTasks.add(task)
                } else if (task.status == TaskStatus.PENDING) {
                    pendingTasks.add(task)
                }
                else
                {
                    deletedTasks.add(task)
                }
            }
        }
        _listDeleteTask.value = deletedTasks
        _listPendingTask.value = pendingTasks
        _listDoneTask.value = doneTasks
    }

    fun addPendingTasks(task: Task)
    {
        _listPendingTask.value?.add(task)
    }

    fun deletePendingTasks(task: Task)
    {
        _listPendingTask.value?.remove(task)
        task.status = TaskStatus.DELETED
        _listDeleteTask.value?.add(task)
    }

    fun deleteDoneTasks(task: Task)
    {
        _listDoneTask.value?.remove(task)
        task.status = TaskStatus.DELETED
        _listDeleteTask.value?.add(task)
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
        var i=0
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
        taskPending?.status = TaskStatus.DONE
        Log.e(this.javaClass.simpleName,taskPending?.title.toString())

        val taskDone = _listDoneTask.value?.find { task -> taskClick.title == task.title }
        taskDone?.status = TaskStatus.PENDING

        if (taskPending != null) {
            _listDoneTask.value?.add(taskPending)
            _listDoneTask.postValue(_listDoneTask.value)

            _listPendingTask.value?.remove(taskPending)

            _listPendingTask.postValue(_listPendingTask.value)
        }

        if(taskDone != null)
        {
            _listPendingTask.value?.add(taskDone)
            _listPendingTask.postValue(_listPendingTask.value)

            _listDoneTask.value?.remove(taskDone)
            _listDoneTask.postValue(_listDoneTask.value)
        }
    }
}
