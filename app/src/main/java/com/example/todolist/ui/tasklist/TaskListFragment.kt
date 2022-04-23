package com.example.todolist.ui.tasklist

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.DialogAddNewTaskBinding
import com.example.todolist.databinding.FragmentTaskListBinding
import com.example.todolist.model.Task
import com.example.todolist.model.TaskStatus
import com.example.todolist.ui.TaskViewModel
import com.example.todolist.ui.dialog.AddNewTaskDialog
import com.example.todolist.utils.ItemTouchListenner
import com.example.todolist.utils.SimpleItemTouchHelperCallback

import java.util.*

class TaskListFragment : Fragment() {
    private val taskViewModel: TaskViewModel by activityViewModels()
    private lateinit var adapter: TaskListAdapter

    private val binding: FragmentTaskListBinding by lazy {
        getInflater(layoutInflater)
    }

    val getInflater: (LayoutInflater) -> FragmentTaskListBinding
        get() = FragmentTaskListBinding::inflate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.taskViewModel = taskViewModel
        adapter = TaskListAdapter(taskViewModel)
        binding.taskListAdapter = adapter

        taskViewModel.openAddTaskDialog.observe(viewLifecycleOwner, {
            if (it == true) {
                showDialog(view)
            }
        })
        addItemTouchCallback(binding.recyclerView)
    }

    private fun addItemTouchCallback(recyclerView: RecyclerView) {
        val callback: ItemTouchHelper.Callback =
            SimpleItemTouchHelperCallback(object : ItemTouchListenner {
                override fun onMove(oldPosition: Int, newPosition: Int) {
                    adapter.onMove(oldPosition, newPosition)
                }

                override fun swipe(position: Int, direction: Int) {
                    adapter.swipe(position, direction)
                }
            })
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    private fun showDialog(view: View) {
        parentFragmentManager.let { AddNewTaskDialog().show(it, "Add new task") }
    }
}