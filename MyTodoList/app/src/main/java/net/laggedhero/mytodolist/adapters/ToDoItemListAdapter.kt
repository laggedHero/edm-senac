package net.laggedhero.mytodolist.adapters

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import net.laggedhero.mytodolist.core.data.ToDoItem
import net.laggedhero.mytodolist.persistence.MyToDoListContract
import net.laggedhero.mytodolist.ui.ToDoListHolder
import net.laggedhero.mytodolist.extensions.*
import net.laggedhero.mytodolist.ui.ToDoListViewHolder

import java.util.Date

class ToDoItemListAdapter() : RecyclerView.Adapter<ToDoListViewHolder>() {

    private var cursor: Cursor? = null

    fun swapCursor(newCursor: Cursor?) {
        cursor?.close()

        cursor = newCursor

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        return ToDoListHolder.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        ToDoListHolder.onBindViewHolder(holder, getToDoItem(position))
    }

    override fun getItemCount() = cursor?.count ?: 0

    override fun getItemId(position: Int): Long {
        return cursor?.let {
            it.moveToPosition(position)
            it.getLong(MyToDoListContract.ToDoItems.COLUMN_ID)
        } ?: 0
    }

    private fun getToDoItem(position: Int): ToDoItem {
        return cursor?.let {
            it.moveToPosition(position)

            ToDoItem(
                    it.getLong(MyToDoListContract.ToDoItems.COLUMN_ID),
                    it.getString(MyToDoListContract.ToDoItems.COLUMN_TITLE),
                    it.getString(MyToDoListContract.ToDoItems.COLUMN_DESCRIPTION),
                    Date(it.getLong(MyToDoListContract.ToDoItems.COLUMN_CREATED_AT)),
                    it.getDate(MyToDoListContract.ToDoItems.COLUMN_DUE_AT)
            )
        } ?: ToDoItem()
    }
}
