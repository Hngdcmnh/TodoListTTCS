package com.example.todolist.model

import java.util.*

data class Task (
    var id: Int,
    var title:String,
    var description:String,
    var deadline:Date
    )