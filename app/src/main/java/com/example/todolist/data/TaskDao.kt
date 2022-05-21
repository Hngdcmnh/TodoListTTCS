package com.example.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todolist.model.Task
import com.example.todolist.model.TaskStatus
import com.example.todolist.utils.dateToString
import java.util.*

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task:Task)

    @Query("DELETE FROM task_database")
    suspend fun deleteAll()

    @Query("DELETE FROM task_database WHERE id = :id")
    suspend fun deleteById(id:Int)

    @Query("SELECT * FROM task_database ORDER BY id ASC")
    fun getAllTask() : LiveData<List<Task>>

    @Query("UPDATE task_database SET title= :title, description = :description, deadline = :deadline, status = :status  WHERE id = :id")
    suspend fun updateTask(id:String, title: String, description:String, deadline: String, status: String)

}