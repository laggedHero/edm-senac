package net.laggedhero.mytodolist.persistence

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri

import java.util.HashMap

class ToDoProvider : ContentProvider() {

    companion object {
        private val URI_MATCHES_TODO_ITEMS = 1
        private val URI_MATCHES_ID_TODO_ITEMS = 2

        private val uriMatcher: UriMatcher
        private val projectionMapToDoItems: HashMap<String, String>

        init {
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(MyToDoListContract.AUTHORITY, MyToDoListContract.ToDoItems.TABLE_NAME, URI_MATCHES_TODO_ITEMS)
            uriMatcher.addURI(MyToDoListContract.AUTHORITY, MyToDoListContract.ToDoItems.TABLE_NAME + "/#", URI_MATCHES_ID_TODO_ITEMS)

            projectionMapToDoItems = HashMap<String, String>()
            projectionMapToDoItems.put(
                    MyToDoListContract.ToDoItems.COLUMN_ID,
                    MyToDoListContract.ToDoItems.COLUMN_ID)
            projectionMapToDoItems.put(
                    MyToDoListContract.ToDoItems.COLUMN_TITLE,
                    MyToDoListContract.ToDoItems.COLUMN_TITLE)
            projectionMapToDoItems.put(
                    MyToDoListContract.ToDoItems.COLUMN_DESCRIPTION,
                    MyToDoListContract.ToDoItems.COLUMN_DESCRIPTION)
            projectionMapToDoItems.put(
                    MyToDoListContract.ToDoItems.COLUMN_CREATED_AT,
                    MyToDoListContract.ToDoItems.COLUMN_CREATED_AT)
            projectionMapToDoItems.put(
                    MyToDoListContract.ToDoItems.COLUMN_DUE_AT,
                    MyToDoListContract.ToDoItems.COLUMN_DUE_AT)
        }
    }

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(): Boolean {
        databaseHelper = DatabaseHelper(context)
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val tableName: String
        val projectionMap: HashMap<String, String>
        val orderBy: String

        when (uriMatcher.match(uri)) {
            URI_MATCHES_TODO_ITEMS -> {
                tableName = MyToDoListContract.ToDoItems.TABLE_NAME
                projectionMap = projectionMapToDoItems
                if (sortOrder.isNullOrEmpty()) {
                    orderBy = MyToDoListContract.ToDoItems.DEFAULT_SORT_ORDER
                } else {
                    orderBy = sortOrder!!
                }
            }
            else -> throw IllegalArgumentException("Unknown URI " + uri)
        }

        val qb = SQLiteQueryBuilder()
        qb.tables = tableName
        qb.setProjectionMap(projectionMap)

        val db = databaseHelper.readableDatabase

        val cursor = qb.query(db, projection, selection, selectionArgs, null, null, orderBy)

        cursor.setNotificationUri(context.contentResolver, uri)

        return cursor
    }

    override fun getType(uri: Uri): String? {
        when (uriMatcher.match(uri)) {
            URI_MATCHES_TODO_ITEMS -> return MyToDoListContract.ToDoItems.CONTENT_TYPE
            URI_MATCHES_ID_TODO_ITEMS -> return MyToDoListContract.ToDoItems.CONTENT_ITEM_TYPE
            else -> throw IllegalArgumentException("Unknown URI " + uri)
        }
    }

    override fun insert(uri: Uri, initialValues: ContentValues?): Uri? {
        var itemUri: Uri? = null
        val tableName: String
        val contentURI: Uri

        when (uriMatcher.match(uri)) {
            URI_MATCHES_TODO_ITEMS -> {
                tableName = MyToDoListContract.ToDoItems.TABLE_NAME
                contentURI = MyToDoListContract.ToDoItems.CONTENT_ID_URI_BASE
            }
            else -> throw IllegalArgumentException("Unknown URI " + uri)
        }

        val values: ContentValues

        if (initialValues != null) {
            values = ContentValues(initialValues)
        } else {
            values = ContentValues()
        }

        val db = databaseHelper.writableDatabase

        val rowId = db.insert(tableName, null, values)

        if (rowId > 0) {
            itemUri = ContentUris.withAppendedId(contentURI, rowId)
            context.contentResolver.notifyChange(itemUri!!, null)
        }

        return itemUri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = databaseHelper.writableDatabase

        val count: Int

        when (uriMatcher.match(uri)) {
            URI_MATCHES_TODO_ITEMS -> count = db.delete(MyToDoListContract.ToDoItems.TABLE_NAME, selection, selectionArgs)
            else -> throw IllegalArgumentException("Unknown URI " + uri)
        }

        context.contentResolver.notifyChange(uri, null)

        return count
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        val db = databaseHelper.writableDatabase

        val count: Int

        when (uriMatcher.match(uri)) {
            URI_MATCHES_TODO_ITEMS -> count = db.update(MyToDoListContract.ToDoItems.TABLE_NAME, values, selection, selectionArgs)
            else -> throw IllegalArgumentException("Unknown URI " + uri)
        }

        context.contentResolver.notifyChange(uri, null)

        return count
    }
}
