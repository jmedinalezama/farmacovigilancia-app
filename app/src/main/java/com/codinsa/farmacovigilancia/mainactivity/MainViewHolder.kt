package com.codinsa.farmacovigilancia.mainactivity

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R

class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var ivImage = itemView.findViewById<ImageView>(R.id.ivItemImageMainActivity)
    private var tvTexto = itemView.findViewById<TextView>(R.id.tvItemTextoMainActivity)

    fun bind(opciones: Opciones, onItemSelected: (Opciones) -> Unit) {
        ivImage.setImageResource(opciones.imagen)
        tvTexto.text = opciones.texto

        itemView.setOnClickListener {
            onItemSelected(opciones)
        }
    }

}