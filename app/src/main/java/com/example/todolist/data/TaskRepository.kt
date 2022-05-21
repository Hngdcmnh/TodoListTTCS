package com.example.todolist.data

import androidx.lifecycle.LiveData
import com.example.todolist.model.Task

class TaskRepository(private var taskDao: TaskDao) {
    var getAllTask: LiveData<List<Task>> = taskDao.getAllTask()

    suspend fun addTask(task: Task)
    {
        taskDao.insertTask(task)
    }

    suspend fun deleteTask(id: Int)
    {
        taskDao.deleteById(id)
    }

    suspend fun updateTask(task: Task)
    {
        taskDao.updateTask(task.id.toString(),task.title,task.description,task.deadline,task.status)
    }
}

