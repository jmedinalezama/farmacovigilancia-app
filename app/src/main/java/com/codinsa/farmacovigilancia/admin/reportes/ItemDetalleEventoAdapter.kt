package com.codinsa.farmacovigilancia.admin.reportes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R

class ItemDetalleEventoAdapter : RecyclerView.Adapter<ItemDetalleEventoViewHolder>() {

    private var listaDetalles = emptyList<ItemDetalleEvento>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDetalleEventoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detalles_evento, parent, false)
        return ItemDetalleEventoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaDetalles.size
    }

    override fun onBindViewHolder(holder: ItemDetalleEventoViewHolder, position: Int) {
        holder.bind(listaDetalles[position])
    }

    fun updateListaDetalles(lista: List<ItemDetalleEvento>) {
        this.listaDetalles = lista
        this.notifyDataSetChanged()
    }
}