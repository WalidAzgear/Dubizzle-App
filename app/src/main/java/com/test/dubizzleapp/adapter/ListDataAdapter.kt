package com.test.dubizzleapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.test.dubizzleapp.R
import com.test.dubizzleapp.model.DataItem
import com.test.dubizzleapp.view.DataItemClickListener

/**
 * This Adapter class to show list of items in the screen.
 */
class ListDataAdapter(var datumItems: List<DataItem>, private val listener: DataItemClickListener) :
    RecyclerView.Adapter<ListDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_data_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datumItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = datumItems[position]
        holder.name.text = item.name
        holder.cardView.setOnClickListener {
            listener.onItemClickListener(item)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name_txt_view)
        val cardView: CardView = view.findViewById(R.id.card_view)
    }
}