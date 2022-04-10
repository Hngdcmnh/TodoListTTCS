package com.example.todolist.data

import com.example.todolist.model.Task
import com.example.todolist.model.TaskStatus
import java.util.*

class TaskDao {
    fun getAllTask():MutableList<Task>
    {
        val list = mutableListOf<Task>()
        for(i in 1..10)
        {
            val cal = Calendar.getInstance().time
            list.add(Task(i,"task $i","description $i",cal,TaskStatus.PENDING))
            list.add(Task(i*10,"task ${i*10}","description $i",cal,TaskStatus.DONE))
        }

        return list
    }
}