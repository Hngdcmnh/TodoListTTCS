package com.example.todolist.ui.deleted

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.FragmentDeletedListBinding
import com.example.todolist.databinding.FragmentDoneListBinding
import com.example.todolist.ui.TaskViewModel
import com.example.todolist.ui.donelist.DoneListAdapter
import com.example.todolist.utils.ItemTouchListenner
import com.example.todolist.utils.SimpleItemTouchHelperCallback

class DeletedListFragment : Fragment() {


    private val taskViewModel: TaskViewModel by activityViewModels()
    private lateinit var adapter: DeletedListAdapter
    private val binding: FragmentDeletedListBinding by lazy {
        getInflater(layoutInflater)
    }

    val getInflater: (LayoutInflater) -> FragmentDeletedListBinding
        get() = FragmentDeletedListBinding::inflate

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
        adapter = DeletedListAdapter(taskViewModel)
        binding.taskDeletedAdapter = adapter
    }
}