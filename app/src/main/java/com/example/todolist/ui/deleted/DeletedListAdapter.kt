package com.example.todolist.ui.deleted

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.base.BaseAdapter
import com.example.todolist.databinding.ItemTaskDeletedBinding
import com.example.todolist.model.Task
import com.example.todolist.ui.TaskViewModel

class DeletedListAdapter(private val taskViewModel: TaskViewModel):
    BaseAdapter<Task, DeletedListAdapter.ViewHolder>()  {

    class ViewHolder(private val binding: ItemTaskDeletedBinding, private val taskViewModel: TaskViewModel) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.task = task
            binding.taskViewModel = taskViewModel
            binding.tvTitleItemTask.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaskDeletedBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding,taskViewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

}