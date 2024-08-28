package com.codinsa.farmacovigilancia.admin.reportes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.admin.model.Evento

class EventoReporteAdapter(
    private val onSelectedDetalles: (Evento) -> Unit,
    private val onSelectedConcomitantes: (Evento) -> Unit,
    private val onSelectedReporte: (Evento) -> Unit
) : RecyclerView.Adapter<EventoReporteViewHolder>() {

    private var listaEventos = emptyList<Evento>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoReporteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evento_reporte, parent, false)
        return EventoReporteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaEventos.size
    }

    override fun onBindViewHolder(holder: EventoReporteViewHolder, position: Int) {
        holder.bind(listaEventos[position], onSelectedDetalles, onSelectedConcomitantes,onSelectedReporte)
    }

    fun updateListaEventos(lista: List<Evento>) {
        this.listaEventos = lista
        this.notifyDataSetChanged()
    }

}