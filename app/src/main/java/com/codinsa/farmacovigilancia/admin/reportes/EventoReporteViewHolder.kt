package com.codinsa.farmacovigilancia.admin.reportes

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.admin.model.Evento
import com.codinsa.farmacovigilancia.utils.FormType
import com.codinsa.farmacovigilancia.utils.GravedadEventoType

class EventoReporteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var ivItem: ImageView = itemView.findViewById(R.id.ivItemEventoReporte)
    private var tvNroControlEvento: TextView = itemView.findViewById(R.id.tvItemNroControlEventoReporte)
    private var tvNombreEvento: TextView = itemView.findViewById(R.id.tvItemNombreEventoReporte)
    private var tvDescripcionEvento: TextView = itemView.findViewById(R.id.tvItemDescripcionEventoReporte)
    private var tvFechaEvento: TextView = itemView.findViewById(R.id.tvItemFechaEventoReporte)
    private var ivVerConcomitantes: ImageView = itemView.findViewById(R.id.ivItemVerConcomitanteEventoReporte)
    private var ivVerPdf: ImageView = itemView.findViewById(R.id.ivItemGenerarPdfEventoReporte)

    private var cvContainerItem: CardView = itemView.findViewById(R.id.cvContainerItemEventoReporte)

    fun bind(evento: Evento, onSelectedDetalles: (Evento) -> Unit, onSelectedConcomitantes: (Evento) -> Unit,onSelectedReporte : (Evento) -> Unit) {

        tvNroControlEvento.text = "N° Control: ${evento.nroControl}"
        tvNombreEvento.text = "Evento: ${evento.nombreEvento}"
        tvDescripcionEvento.text = evento.descripcion
        tvFechaEvento.text = "${evento.fechaInicio} - ${evento.fechaFin}"

        if(evento.tipoFormulario.lowercase() == FormType.PROFESIONAL.name.lowercase()) {
            ivItem.setImageResource(R.drawable.ic_profesional)
        } else {
            ivItem.setImageResource(R.drawable.ic_paciente)
        }

        when(evento.gravedadEvento.lowercase()) {
            GravedadEventoType.LEVE.name.lowercase() -> {
                cvContainerItem
                    .setCardBackgroundColor(itemView.resources.getColor(R.color.leve_color, null))
            }
            GravedadEventoType.MODERADO.name.lowercase() -> {
                cvContainerItem
                    .setCardBackgroundColor(itemView.resources.getColor(R.color.moderado_color, null))
            }
            GravedadEventoType.SEVERO.name.lowercase() -> {
                cvContainerItem
                    .setCardBackgroundColor(itemView.resources.getColor(R.color.severo_color, null))
            }
        }

        ivItem.setOnClickListener {
            onSelectedDetalles(evento)
        }

        ivVerConcomitantes.setOnClickListener {
            onSelectedConcomitantes(evento)
        }
        ivVerPdf.setOnClickListener{
            onSelectedReporte(evento)
        }
    }

}