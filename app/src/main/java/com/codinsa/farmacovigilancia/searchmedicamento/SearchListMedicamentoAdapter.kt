package com.codinsa.farmacovigilancia.searchmedicamento

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.network.response.SearchMedicamentoResponse

class SearchListMedicamentoAdapter(val onItemSelected: (SearchMedicamentoResponse) -> Unit)
    : RecyclerView.Adapter<SearchListMedicamentoViewHolder>() {

    private var medicamentosList = emptyList<SearchMedicamentoResponse>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListMedicamentoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_medicamento, parent, false)
        return SearchListMedicamentoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return medicamentosList.size
    }

    override fun onBindViewHolder(holder: SearchListMedicamentoViewHolder, position: Int) {
        holder.bind(medicamentosList[position], onItemSelected)
    }

    fun updateList(lista: List<SearchMedicamentoResponse>) {
        this.medicamentosList = lista
        this.notifyDataSetChanged()
    }


}