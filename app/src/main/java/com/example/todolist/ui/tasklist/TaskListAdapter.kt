package com.example.todolist.ui.tasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.model.Task

class TaskListAdapter: RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    var listData:MutableList<Task> = mutableListOf()

    class ViewHolder(val binding: ItemTaskBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bind(task: Task)
        {
            binding.task = task
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

}