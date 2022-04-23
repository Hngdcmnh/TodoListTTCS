package com.example.todolist.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SimpleItemTouchHelperCallback(mListenner: ItemTouchListenner) :
    ItemTouchHelper.Callback() {
    private val mListenner: ItemTouchListenner


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlag: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlag: Int = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlag, swipeFlag)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        mListenner.onMove(viewHolder.adapterPosition, target.adapterPosition)
        return false
    }



    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mListenner.swipe(viewHolder.adapterPosition, direction)
    }

    init {
        this.mListenner = mListenner
    }
}