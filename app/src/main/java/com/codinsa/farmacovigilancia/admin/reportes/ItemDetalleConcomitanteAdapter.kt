package com.codinsa.farmacovigilancia.admin.reportes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.admin.model.Concomitante

class ItemDetalleConcomitanteAdapter : RecyclerView.Adapter<ItemDetalleConcomitanteViewHolder>() {

    private var listaConcomitantes = emptyList<Concomitante>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemDetalleConcomitanteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detalles_concomitante, parent, false)
        return ItemDetalleConcomitanteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaConcomitantes.size
    }

    override fun onBindViewHolder(holder: ItemDetalleConcomitanteViewHolder, position: Int) {
        holder.bind(listaConcomitantes[position])
    }

    fun updateListaConcomitantes(lista: List<Concomitante>) {
        this.listaConcomitantes = lista
        this.notifyDataSetChanged()
    }


}