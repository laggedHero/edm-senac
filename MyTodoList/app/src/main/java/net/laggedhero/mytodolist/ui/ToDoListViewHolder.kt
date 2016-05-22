package net.laggedhero.mytodolist.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.todo_list_item.view.*

class ToDoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var id: Long = 0

    var title: String?
        get() = itemView.todo_title.text.toString()
        set(value) {
            itemView.todo_title.text = value
        }
}