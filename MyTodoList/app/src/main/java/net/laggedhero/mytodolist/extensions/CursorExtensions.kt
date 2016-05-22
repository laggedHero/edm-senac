package net.laggedhero.mytodolist.extensions

import android.database.Cursor
import java.util.*

fun Cursor.getLong(columnName: String): Long {
    return getLong(getColumnIndex(columnName))
}

fun Cursor.getString(columnName: String): String {
    return getString(getColumnIndex(columnName))
}

fun Cursor.getDate(columnName: String): Date? {
    val timestamp = getLong(columnName)
    return if (timestamp > 0) Date(timestamp) else null
}