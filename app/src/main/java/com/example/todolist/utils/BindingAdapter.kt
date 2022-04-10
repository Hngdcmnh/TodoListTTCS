package com.example.todolist.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.base.BaseAdapter
import com.example.todolist.model.Task

@BindingAdapter("submitList")
fun submitList(recyclerView: RecyclerView, list:MutableList<Task>)
{
    val adapter = recyclerView.adapter as BaseAdapter<Task,RecyclerView.ViewHolder>
    adapter.updateList(list)
}
