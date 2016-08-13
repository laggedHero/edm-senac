package net.laggedhero.anotherapplication.realmlist.newitem

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import kotlinx.android.synthetic.main.activity_realm_new_item.*
import net.laggedhero.anotherapplication.AnotherApplication
import net.laggedhero.anotherapplication.R
import net.laggedhero.anotherapplication.realmlist.data.Task
import java.util.*

class RealmNewItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realm_new_item)

        saveTask.setOnClickListener { attemptSave() }
    }

    private fun attemptSave() {
        if (isNotValid()) {
            showError(null)
            return
        }

        val task = Task()
        task.nome = name.text.toString()
        task.descricao = description.text.toString()
        task.local = location.text.toString()

        (application as AnotherApplication).realm.executeTransactionAsync(
                { it.copyToRealmOrUpdate(task) },
                { finish() },
                { showError(it.message) }
        )
    }

    private fun isNotValid(): Boolean {
        return name.text.toString().isNullOrBlank()
    }

    private fun showError(message: String?) {
        Snackbar.make(saveTask, message ?: "TROLOLO", Snackbar.LENGTH_LONG).show()
    }
}
