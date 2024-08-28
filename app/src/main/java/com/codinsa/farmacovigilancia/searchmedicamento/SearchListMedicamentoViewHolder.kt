package com.codinsa.farmacovigilancia.searchmedicamento

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.network.response.SearchMedicamentoResponse

class SearchListMedicamentoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var ivImage: ImageView = itemView.findViewById(R.id.ivItemImageSearchMedicamento)
    private var tvNombre: TextView = itemView.findViewById(R.id.tvItemNombreSearchMedicamento)

    fun bind(medicamento: SearchMedicamentoResponse, onItemSelected: (SearchMedicamentoResponse) -> Unit) {

        tvNombre.text = medicamento.nombre

        val options = RequestOptions()
            .placeholder(R.drawable.ic_medicamento_default)
            .error(R.drawable.ic_medicamento_default)

        ivImage.let {
            Glide.with(it)
                .setDefaultRequestOptions(options)
                .load(medicamento.fotos?.let { fotos ->
                    fotos[0].url
                })
                .into(it)
        }

        itemView.setOnClickListener {
            onItemSelected(medicamento)
        }

    }

}