package com.example.todolist.ui.donelist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.todolist.base.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.model.Task
import com.example.todolist.ui.TaskViewModel
import com.example.todolist.ui.donelist.DoneListAdapter.*
import java.util.*

class DoneListAdapter(private val taskViewModel: TaskViewModel):BaseAdapter<Task, DoneListAdapter.ViewHolder>()  {

    class ViewHolder(private val binding: ItemTaskBinding,private val taskViewModel: TaskViewModel) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.task = task
            binding.taskViewModel = taskViewModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding,taskViewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    fun swipe(position: Int, direction: Int) {
        taskViewModel.deleteDoneTasks(listData[position])
        notifyItemRemoved(position)
    }

    fun onMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(listData, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(listData, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }
}