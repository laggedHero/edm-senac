package net.laggedhero.anotherapplication.realmlist

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.realm.RealmResults
import net.laggedhero.anotherapplication.realmlist.data.Task

class RealmListAdapter(val taskResults: RealmResults<Task>) : RecyclerView.Adapter<RealmListItemView.RealmListItemViewHolder>() {

    override fun getItemCount(): Int {
        return taskResults.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealmListItemView.RealmListItemViewHolder? {
        return RealmListItemView.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RealmListItemView.RealmListItemViewHolder, position: Int) {
        RealmListItemView.onBindViewHolder(holder, taskResults.get(position))
    }

}