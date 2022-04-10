package com.example.todolist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.model.Task
import com.example.todolist.model.TaskStatus
import com.example.todolist.utils.Event
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

    private var _addTaskEvent = MutableLiveData<Event<Any>>()
    val addTaskEvent: LiveData<Event<Any>>
        get() = _addTaskEvent

    init {
        val tasks = mutableListOf<Task>()
        for (i in 1..10) {
            val cal = Calendar.getInstance().time
            tasks.add(Task(i, "task $i", "description $i", cal, TaskStatus.PENDING))
            tasks.add(Task(i * 10, "task ${i * 10}", "description $i", cal, TaskStatus.DONE))
        }
        _listTask.value = tasks

        updateListDoneTask()
    }

    fun updateListDoneTask() {
        val pendingTasks = mutableListOf<Task>()
        val doneTasks = mutableListOf<Task>()
        var deletedTasks = mutableListOf<Task>()
        _listTask.value?.let {
            for (task in it) {
                if (task.status == TaskStatus.DONE) {
                    doneTasks.add(task)
                } else if (task.status == TaskStatus.PENDING) {
                    pendingTasks.add(task)
                }
            }
        }
        _listPendingTask.value = pendingTasks
        _listDoneTask.value = doneTasks
    }

//    fun eventAddNewTask(){
//        _addTaskEvent.value = Event(Task())
//    }



}