package com.example.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.utils.dateToString
import java.io.Serializable
import java.util.*

@Entity(tableName = "task_database")
data class Task (
    @PrimaryKey
    var id: Int,
    var title:String,
    var description:String,
    var deadline:String,
    var status:String
    ){

    val deadlineForView: String
        get() = deadline

}

