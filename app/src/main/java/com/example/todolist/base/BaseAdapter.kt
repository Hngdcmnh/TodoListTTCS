package com.example.todolist.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.model.Task

abstract class BaseAdapter<T,V:RecyclerView.ViewHolder>:ListAdapter<T,V>(ItemDiffUtil()) {
    var listData: MutableList<T> = mutableListOf()

    open fun updateList(list: MutableList<T>)
    {
        this.listData = list
        notifyDataSetChanged()
    }

//    override fun submitList(list: MutableList<T>?) {
//        list?.let {
//            this.listData = it
//        }
//        super.submitList(list)
//    }

    class ItemDiffUtil<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {

            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }
}