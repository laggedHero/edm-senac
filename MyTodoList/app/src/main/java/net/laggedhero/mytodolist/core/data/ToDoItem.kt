package net.laggedhero.mytodolist.core.data

import java.util.Date

data class ToDoItem(
        var id: Long = 0,
        var title: String = "",
        var description: String? = null,
        var createdAt: Date = Date(),
        var dueAt: Date? = null
)
