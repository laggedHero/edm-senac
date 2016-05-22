package net.laggedhero.mytodolist

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_add_item.*

import net.laggedhero.mytodolist.persistence.MyToDoListContract

import java.util.Date

class AddItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        todo_submit.setOnClickListener {
            saveTodoItem()
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveTodoItem() {
        val values = ContentValues()
        values.put(MyToDoListContract.ToDoItems.COLUMN_TITLE, todo_title_edit_text.text.toString())
        values.put(MyToDoListContract.ToDoItems.COLUMN_DESCRIPTION, todo_desc_edit_text.text.toString())
        values.put(MyToDoListContract.ToDoItems.COLUMN_CREATED_AT, Date().time)

        contentResolver.insert(MyToDoListContract.ToDoItems.CONTENT_URI, values)
    }
}
