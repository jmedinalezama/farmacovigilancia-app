package com.codinsa.farmacovigilancia.searchmedicamento

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.network.response.SearchMedicamentoResponse
import com.google.android.material.textfield.TextInputEditText

class SearchListMedicamentoActivity : AppCompatActivity() {

    private lateinit var etBuscar: TextInputEditText
    private lateinit var btnBuscar: Button
    private lateinit var rvMedicamentos: RecyclerView

    private lateinit var viewModelSearch: SearchListMedicamentoViewModel

    private lateinit var adapterSearch: SearchListMedicamentoAdapter

    companion object {
        const val N_REGISTRO = "nregistro"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_search_list_medicamento)

        viewModelSearch = ViewModelProvider(this)[SearchListMedicamentoViewModel::class.java]

        etBuscar = findViewById(R.id.etBuscarSearchMedicamento)
        btnBuscar = findViewById(R.id.btnBuscarSearchMedicamento)
        rvMedicamentos = findViewById(R.id.rvSearchMedicamentos)

        adapterSearch = SearchListMedicamentoAdapter { medicamento ->
            verDetallesMedicamento(medicamento)
        }
        rvMedicamentos.layoutManager = LinearLayoutManager(this)
        rvMedicamentos.adapter = adapterSearch

        btnBuscar.setOnClickListener {
            val nombre = etBuscar.text.toString()
            viewModelSearch.getListMedicamentosPorNombre(nombre)
        }

        observerSearch()
    }

    private fun observerSearch() {
        viewModelSearch.medicamentosListService.observe(this) {
            it?.let {
                adapterSearch.updateList(it)
            }
        }
    }

    private fun verDetallesMedicamento(medicamento: SearchMedicamentoResponse) {

        val intent = Intent(this, SearchDetalleMedicamentoActivity::class.java)

        intent.putExtra(N_REGISTRO, medicamento.nregistro)

        startActivity(intent)
    }
}