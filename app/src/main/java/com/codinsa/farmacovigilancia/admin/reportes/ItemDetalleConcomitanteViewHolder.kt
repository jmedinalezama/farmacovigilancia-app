package com.codinsa.farmacovigilancia.admin.reportes

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.admin.model.Concomitante

class ItemDetalleConcomitanteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var tvItemNombre = itemView.findViewById<TextView>(R.id.tvItemDetalleConcomitanteNombre)
    private var tvItemDosis = itemView.findViewById<TextView>(R.id.tvItemDetalleConcomitanteDosis)
    private var tvItemFrecuencia = itemView.findViewById<TextView>(R.id.tvItemDetalleConcomitanteFrecuencia)
    private var tvItemVia = itemView.findViewById<TextView>(R.id.tvItemDetalleConcomitanteViaAdministracion)
    private var tvItemFecha = itemView.findViewById<TextView>(R.id.tvItemDetalleConcomitanteFecha)

    fun bind(concomitante: Concomitante) {
        tvItemNombre.text = "Nombre farmaco: ${concomitante.nombre}"
        tvItemDosis.text = "Dosis: ${concomitante.dosis}"
        tvItemFrecuencia.text = "Frecuencia: ${concomitante.frecuencia}"
        tvItemVia.text = "Vía administración: ${concomitante.viaAdministracion}"
        tvItemFecha.text = "${concomitante.fechaInicio} - ${concomitante.fechaFin}"
    }

}