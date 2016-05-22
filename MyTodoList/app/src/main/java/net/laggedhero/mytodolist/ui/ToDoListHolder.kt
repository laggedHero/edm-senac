package net.laggedhero.mytodolist.ui

import android.view.LayoutInflater
import android.view.ViewGroup

import net.laggedhero.mytodolist.R
import net.laggedhero.mytodolist.core.data.ToDoItem

object ToDoListHolder {

    fun onCreateViewHolder(parent: ViewGroup): ToDoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_item, parent, false)
        return ToDoListViewHolder(view)
    }

    fun onBindViewHolder(holder: ToDoListViewHolder, toDoItem: ToDoItem) {
        holder.id = toDoItem.id
        holder.title = toDoItem.title
    }
}
