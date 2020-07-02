package com.gorrotowi.cert102recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list.view.*
import kotlin.properties.Delegates

class RcListAdapter : RecyclerView.Adapter<RcListAdapter.ListViewHolder>() {

    var dataSource: MutableList<ItemModel>? by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = dataSource?.size ?: 0

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        dataSource?.let {
            holder.bind(it[position])
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: ItemModel) {
            itemView.txtItemTitle.text = data.title
            itemView.txtItemDescr.text = data.desc
        }
    }
}
