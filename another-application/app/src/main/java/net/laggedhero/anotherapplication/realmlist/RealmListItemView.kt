package net.laggedhero.anotherapplication.realmlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.realm_list_item.view.*
import net.laggedhero.anotherapplication.R
import net.laggedhero.anotherapplication.realmlist.data.Task

class RealmListItemView {

    companion object {
        fun onCreateViewHolder(parent: ViewGroup): RealmListItemView.RealmListItemViewHolder {
            return RealmListItemViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.realm_list_item, parent, false)
            )
        }

        fun onBindViewHolder(holder: RealmListItemView.RealmListItemViewHolder, task: Task) {
            holder.setTask(task)
        }
    }

    class RealmListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setTask(task: Task) {
            itemView.name.text = task.nome
        }
    }
}