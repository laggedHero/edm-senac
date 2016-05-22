package net.laggedhero.mytodolist.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DatabaseHelper.DB_NAME, null, DatabaseHelper.DB_VERSION) {

    companion object {
        private val DB_NAME = "todo-list.db"
        private val DB_VERSION = 1
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        val createTodoItems = """
            CREATE TABLE ${MyToDoListContract.ToDoItems.TABLE_NAME} (
                ${MyToDoListContract.ToDoItems.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${MyToDoListContract.ToDoItems.COLUMN_TITLE} TEXT,
                ${MyToDoListContract.ToDoItems.COLUMN_DESCRIPTION} TEXT,
                ${MyToDoListContract.ToDoItems.COLUMN_CREATED_AT} INTEGER,
                ${MyToDoListContract.ToDoItems.COLUMN_DUE_AT} INTEGER
            );
        """
        sqLiteDatabase.execSQL(createTodoItems)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}
