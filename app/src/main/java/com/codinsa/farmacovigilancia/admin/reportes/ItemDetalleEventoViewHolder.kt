package com.codinsa.farmacovigilancia.admin.reportes

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R

class ItemDetalleEventoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvKey = itemView.findViewById<TextView>(R.id.tvItemKeyDetalleEvento)
    private val tvValue = itemView.findViewById<TextView>(R.id.tvItemValueDetalleEvento)

    fun bind(item: ItemDetalleEvento) {
        tvKey.text = item.key
        tvValue.text = item.value.toString()
    }

}