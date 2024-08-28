package com.codinsa.farmacovigilancia.admin.reportes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R

class FiltrosAdapter(var onFiltroSelected: (Filtro) -> Unit): RecyclerView.Adapter<FiltrosViewHolder>() {

    private var listFiltros = emptyList<Filtro>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltrosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter_reportes, parent, false)
        return FiltrosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFiltros.size
    }

    override fun onBindViewHolder(holder: FiltrosViewHolder, position: Int) {
        holder.bind(listFiltros[position], onFiltroSelected)
    }

    fun updateListaFiltros(lista: List<Filtro>) {
        this.listFiltros = lista
        this.notifyItemRangeChanged(0, listFiltros.size)
    }
}