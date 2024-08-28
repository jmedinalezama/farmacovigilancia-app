package com.codinsa.farmacovigilancia.concomitante

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.network.request.Concomitante

class ConcomitanteAdapter(private val onDeletedConcomitante: (Concomitante) -> Unit)
    : RecyclerView.Adapter<ConcomitanteViewHolder>() {

    private var lstConcomitante = emptyList<Concomitante>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcomitanteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_concomitante, parent, false)
        return ConcomitanteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lstConcomitante.size
    }

    override fun onBindViewHolder(holder: ConcomitanteViewHolder, position: Int) {
        holder.bind(lstConcomitante[position], onDeletedConcomitante)
    }

    fun updateListConcomitantes(lista: List<Concomitante>) {
        this.lstConcomitante = lista
        this.notifyDataSetChanged()
    }

}