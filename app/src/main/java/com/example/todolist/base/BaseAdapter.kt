package com.example.todolist.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T,V:RecyclerView.ViewHolder>:RecyclerView.Adapter<V>() {
    var listData: MutableList<T> = mutableListOf()

    open fun updateList(list: MutableList<T>)
    {
        this.listData = list
        notifyDataSetChanged()
    }
}