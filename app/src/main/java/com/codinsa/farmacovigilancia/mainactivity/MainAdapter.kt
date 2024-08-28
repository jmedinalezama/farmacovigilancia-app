package com.codinsa.farmacovigilancia.mainactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R

class MainAdapter (private var onItemSelected: (Opciones) -> Unit) : RecyclerView.Adapter<MainViewHolder>() {

    private var listaOpciones = emptyList<Opciones>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_activity, parent, false)
        return  MainViewHolder(view)
    }

    override fun getItemCount(): Int {
       return listaOpciones.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(listaOpciones[position], onItemSelected)
    }

    fun updateListaOpciones(lista: List<Opciones>) {
        this.listaOpciones = lista
        this.notifyDataSetChanged()
    }
}