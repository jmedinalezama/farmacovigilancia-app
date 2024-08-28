package com.codinsa.farmacovigilancia.admin.reportes

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R

class FiltrosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvNombreFiltro: TextView = itemView.findViewById(R.id.tvItemNombreFiltro)
    private val cvFiltro: CardView = itemView.findViewById(R.id.cvItemFiltro)

    fun bind(filtro: Filtro, onFiltroSelected: (Filtro) -> Unit) {

        if(filtro.isSelected) {
            cvFiltro.setCardBackgroundColor(itemView.resources.getColor(R.color.first_color_logo, null))
            tvNombreFiltro.setTextColor(itemView.resources.getColor(R.color.light, null))
        } else {
            cvFiltro.setCardBackgroundColor(itemView.resources.getColor(R.color.light, null))
            tvNombreFiltro.setTextColor(itemView.resources.getColor(R.color.dark, null))
        }

        tvNombreFiltro.text = filtro.nombre

        itemView.setOnClickListener {
            onFiltroSelected(filtro)
        }

    }
}