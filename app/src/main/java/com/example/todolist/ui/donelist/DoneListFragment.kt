package com.example.todolist.ui.donelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.FragmentDoneListBinding
import com.example.todolist.databinding.FragmentTaskListBinding
import com.example.todolist.ui.TaskViewModel
import com.example.todolist.ui.tasklist.TaskListAdapter

class DoneListFragment : Fragment() {

    private val taskViewModel: TaskViewModel by activityViewModels()

    private val binding: FragmentDoneListBinding by lazy {
        getInflater(layoutInflater)
    }

    val getInflater: (LayoutInflater) -> FragmentDoneListBinding
        get() = FragmentDoneListBinding::inflate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.taskViewModel = taskViewModel
        binding.adapter = DoneListAdapter()

    }

}