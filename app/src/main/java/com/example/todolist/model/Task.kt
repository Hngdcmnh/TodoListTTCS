package com.example.todolist.model

import com.example.todolist.utils.dateToString
import java.util.*

data class Task (
    var id: Int,
    var title:String,
    var description:String,
    var deadline:Date,
    var status:TaskStatus
    ){

    val deadlineForView: String
        get() = deadline.dateToString("dd/MM/yyyy")


}