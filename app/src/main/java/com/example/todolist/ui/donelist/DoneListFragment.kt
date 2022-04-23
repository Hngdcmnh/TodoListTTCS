package com.example.todolist.ui.donelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.FragmentDoneListBinding
import com.example.todolist.databinding.FragmentTaskListBinding
import com.example.todolist.ui.TaskViewModel
import com.example.todolist.ui.tasklist.TaskListAdapter
import com.example.todolist.utils.ItemTouchListenner
import com.example.todolist.utils.SimpleItemTouchHelperCallback

class DoneListFragment : Fragment() {

    private val taskViewModel: TaskViewModel by activityViewModels()
    private lateinit var adapter:DoneListAdapter

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
        adapter = DoneListAdapter(taskViewModel)
        binding.doneListAdapter = adapter

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

}