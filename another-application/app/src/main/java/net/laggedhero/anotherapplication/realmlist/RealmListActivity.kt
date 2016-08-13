package net.laggedhero.anotherapplication.realmlist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_realm_list.*
import kotlinx.android.synthetic.main.content_realm_list.*
import net.laggedhero.anotherapplication.AnotherApplication

import net.laggedhero.anotherapplication.R
import net.laggedhero.anotherapplication.realmlist.data.Task
import net.laggedhero.anotherapplication.realmlist.newitem.RealmNewItemActivity

class RealmListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realm_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            startActivity(Intent(this@RealmListActivity, RealmNewItemActivity::class.java))
        }

        realmListRecyclerView.layoutManager = LinearLayoutManager(this)

        val result = (application as AnotherApplication).realm.where(Task::class.java).findAllAsync()
        result.addChangeListener {
            val sortedResults = it.sort("termino", Sort.ASCENDING)
            realmListRecyclerView.adapter = RealmListAdapter(sortedResults)
        }
    }

}
