package net.laggedhero.mytodolist

import android.content.Intent
import android.database.Cursor
import android.graphics.*
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

import net.laggedhero.mytodolist.adapters.ToDoItemListAdapter
import net.laggedhero.mytodolist.extensions.dpToPx
import net.laggedhero.mytodolist.persistence.MyToDoListContract

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        private val TODO_LIST_LOADER = 0
    }

    private lateinit var adapter: ToDoItemListAdapter
    private val paint = Paint()
    private val checkBitmap by lazy { BitmapFactory.decodeResource(resources, R.drawable.ic_check_white_24dp) }
    private val dollyBitmap by lazy { BitmapFactory.decodeResource(resources, R.drawable.dolly) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { showAddItemScreen() }

        paint.color = Color.parseColor("#4CAF50")

        adapter = ToDoItemListAdapter()

        todo_recycler_view.layoutManager = LinearLayoutManager(this)
        todo_recycler_view.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction.equals(ItemTouchHelper.LEFT)) {
                    failDelete(viewHolder.adapterPosition)
                } else {
                    uberFailDelete(viewHolder.adapterPosition)
                }
            }

            override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val itemView = viewHolder.itemView

                if (dX > 0) {
                    // positive
                    canvas.drawRect(
                            itemView.left.toFloat(),
                            itemView.top.toFloat(),
                            dX,
                            itemView.bottom.toFloat(),
                            paint
                    );

                    canvas.drawBitmap(
                            checkBitmap,
                            itemView.left.toFloat() + itemView.context.dpToPx(16f),
                            itemView.top.toFloat() + itemView.height / 2 - checkBitmap.height / 2,
                            paint
                    )
                } else {
                    // negative
                    canvas.drawRect(
                            itemView.right.toFloat() + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat(),
                            paint
                    );

                    canvas.drawBitmap(
                            dollyBitmap,
                            itemView.right.toFloat() - dollyBitmap.width - itemView.context.dpToPx(16f),
                            itemView.top.toFloat() + itemView.height / 2 - dollyBitmap.height / 2,
                            paint
                    )
                }

                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        })

        itemTouchHelper.attachToRecyclerView(todo_recycler_view)
    }

    override fun onStart() {
        super.onStart()

        supportLoaderManager.restartLoader(TODO_LIST_LOADER, Bundle(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_fail_all) {
            failAll();
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showAddItemScreen() {
        val intent = Intent(this, AddItemActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateLoader(id: Int, args: Bundle): Loader<Cursor>? {
        if (id == TODO_LIST_LOADER) {
            return onCreateToDoListLoader()
        }
        return null
    }

    private fun onCreateToDoListLoader(): Loader<Cursor> {
        val projection = arrayOf(
                MyToDoListContract.ToDoItems.COLUMN_ID,
                MyToDoListContract.ToDoItems.COLUMN_TITLE,
                MyToDoListContract.ToDoItems.COLUMN_DESCRIPTION,
                MyToDoListContract.ToDoItems.COLUMN_CREATED_AT,
                MyToDoListContract.ToDoItems.COLUMN_DUE_AT
        )

        return CursorLoader(this, MyToDoListContract.ToDoItems.CONTENT_URI, projection, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        if (loader.id == TODO_LIST_LOADER) {
            adapter.swapCursor(data)
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (loader.id == TODO_LIST_LOADER) {
            adapter.swapCursor(null)
        }
    }

    private fun failAll() {
        if (contentResolver.delete(MyToDoListContract.ToDoItems.CONTENT_URI, null, null) > 0) {
            showSnackBar(getString(R.string.fail_all_motivational))
        } else {
            showSnackBar(getString(R.string.fail_all_already))
        }
    }

    private fun delete(adapterPosition: Int) {
        val where = MyToDoListContract.ToDoItems.COLUMN_ID + " = ?"
        val whereArgs = arrayOf(adapter.getItemId(adapterPosition).toString())

        contentResolver.delete(MyToDoListContract.ToDoItems.CONTENT_URI, where, whereArgs)
    }

    private fun failDelete(adapterPosition: Int) {
        delete(adapterPosition)
        showSnackBar(getString(R.string.fail_item))
    }

    private fun uberFailDelete(adapterPosition: Int) {
        delete(adapterPosition)

        AlertDialog.Builder(this).setView(R.layout.dialog_uber_fail).show()
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(fab, message, Snackbar.LENGTH_LONG).show()
    }
}
