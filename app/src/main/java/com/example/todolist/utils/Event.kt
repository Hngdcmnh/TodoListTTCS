package com.example.todolist.utils

import androidx.lifecycle.Observer

open class Event<T>(private var content: T) {
    var hasBeenHandled = false
        private set

    fun setContent(content: T)
    {
        this.content = content
    }

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}