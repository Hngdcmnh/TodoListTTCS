package com.example.todolist.ui.dialog

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.todolist.R
import com.example.todolist.databinding.DialogAddNewTaskBinding
import com.example.todolist.databinding.FragmentTaskListBinding
import com.example.todolist.model.Task
import com.example.todolist.model.TaskStatus
import com.example.todolist.ui.TaskViewModel
import java.util.*
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast


class AddNewTaskDialog:DialogFragment() {

    private val taskViewModel: TaskViewModel by activityViewModels()
    private val calendar= Calendar.getInstance()

    private val binding: DialogAddNewTaskBinding by lazy {
        getInflater(layoutInflater)
    }

    val getInflater: (LayoutInflater) -> DialogAddNewTaskBinding
        get() = DialogAddNewTaskBinding::inflate

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner);
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendar.visibility = View.GONE

        binding.btCancel.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btSave.setOnClickListener {
            if(checkTask())
            {
                taskViewModel.addPendingTasks(Task(getID(),binding.etTitle.text.toString(),binding.etDescription.text.toString(),calendar.time,TaskStatus.PENDING))
                dialog?.dismiss()
            }

        }

        binding.tvDeadline.setOnClickListener {

            if(binding.calendar.visibility == View.VISIBLE)
            {
                binding.calendar.visibility=View.GONE
            }
            else{
                binding.calendar.visibility = View.VISIBLE
            }
        }

        binding.calendar.setOnDateChangeListener( OnDateChangeListener{ _, year, month, dayOfMonth ->
            binding.tvDeadline.text = (String.format("%02d/%02d/%02d", dayOfMonth, month, year))
            calendar.set(year, month, dayOfMonth, 0, 0);
            binding.calendar.visibility = View.GONE
        })
    }

    private fun getID(): Int {
        var idString = UUID.randomUUID().toString()
        var res =0
        for(i in idString)
        {
            if(i.isDigit())
            {
                res = res*10+i.digitToInt()
            }
        }

        return res
    }

    private fun checkTask():Boolean {
        return if(binding.etTitle.text.toString().length<3) {
            Toast.makeText(this.context,"Title is too short",Toast.LENGTH_LONG).show()
            false
        } else {
            true
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        taskViewModel.dismissAddNewTaskDialog()
    }
}