package com.codinsa.farmacovigilancia.concomitante

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.network.request.Concomitante

class ConcomitanteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var tvNombre: TextView = itemView.findViewById(R.id.tvNombreConcomitante)
    private var ivEliminar: ImageView = itemView.findViewById(R.id.btnEliminarConcomitante)

    fun bind(concomitante: Concomitante, onDeletedConcomitante: (Concomitante) -> Unit) {

        tvNombre.text = concomitante.nombre

        ivEliminar.setOnClickListener {
            onDeletedConcomitante(concomitante)
        }
    }

}