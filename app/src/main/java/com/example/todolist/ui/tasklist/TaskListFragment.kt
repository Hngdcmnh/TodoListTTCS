package com.example.todolist.ui.tasklist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.FragmentTaskListBinding
import com.example.todolist.model.Task
import com.example.todolist.model.TaskStatus
import com.example.todolist.ui.TaskViewModel
import java.util.*


class TaskListFragment : Fragment() {

    private val taskViewModel:TaskViewModel by activityViewModels()

    private val binding: FragmentTaskListBinding by lazy {
        getInflater(layoutInflater)
    }

    val getInflater: (LayoutInflater) -> FragmentTaskListBinding
        get() = FragmentTaskListBinding::inflate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.taskViewModel = taskViewModel
        binding.taskListAdapter = TaskListAdapter()

    }
}