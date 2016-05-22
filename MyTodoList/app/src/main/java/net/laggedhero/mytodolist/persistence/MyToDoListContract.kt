package net.laggedhero.mytodolist.persistence

import android.net.Uri
import android.provider.BaseColumns

object MyToDoListContract {
    val AUTHORITY = "net.laggedhero.mytodolist"

    class ToDoItems private constructor() : BaseColumns {
        companion object {

            /**
             * The table name offered by this provider
             */
            val TABLE_NAME = "todoitems"

            /**
             * URI definitions
             */

            /**
             * The scheme part for this provider's URI
             */
            private val SCHEME = "content://"

            /**
             * Path parts for the URIs
             */

            /**
             * Path part for the Ingredients URI
             */
            private val PATH = "/" + TABLE_NAME

            /**
             * Path part for the Ingredients ID URI
             */
            private val PATH_ID = "/$TABLE_NAME/"

            /**
             * 0-relative position of a note ID segment in the path part of a note
             * ID URI
             */
            val ID_PATH_POSITION = 1

            /**
             * Path part for the Live Folder URI
             */
            private val PATH_LIVE_FOLDER = "/live_folders/" + TABLE_NAME

            /**
             * The content:// style URL for this table
             */
            val CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH)

            /**
             * The content URI base for a single account. Callers must append a
             * numeric account id to this Uri to retrieve a note
             */
            val CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_ID)

            /**
             * The content URI match pattern for a single account, specified by its
             * ID. Use this to match incoming URIs or to construct an Intent.
             */
            val CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + AUTHORITY + PATH_ID + "/#")

            /**
             * The content Uri pattern for a notes listing for live folders
             */
            val LIVE_FOLDER_URI = Uri.parse(SCHEME + AUTHORITY + PATH_LIVE_FOLDER)

            /**
             * MIME type definitions
             */

            /**
             * The MIME type of [.CONTENT_URI] providing a directory of
             * accounts.
             */
            val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.laggedhero.mytodolist.todoitems"

            /**
             * The MIME type of a [.CONTENT_URI] sub-directory of a single
             * note.
             */
            val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.laggedhero.mytodolist.todoitems"

            val COLUMN_ID = "_id"
            val COLUMN_TITLE = "title"
            val COLUMN_DESCRIPTION = "description"
            val COLUMN_CREATED_AT = "createdAt"
            val COLUMN_DUE_AT = "dueAt"

            /**
             * The default sort order for this table
             */
            val DEFAULT_SORT_ORDER = COLUMN_CREATED_AT + " ASC"
        }
    }
}
