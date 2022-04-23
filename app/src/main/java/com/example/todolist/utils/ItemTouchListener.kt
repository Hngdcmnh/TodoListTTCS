package com.example.todolist.utils

interface ItemTouchListenner {
    fun onMove(oldPosition: Int, newPosition: Int)
    fun swipe(position: Int, direction: Int)
}