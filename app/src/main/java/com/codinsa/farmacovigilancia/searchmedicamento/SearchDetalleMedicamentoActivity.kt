package com.codinsa.farmacovigilancia.searchmedicamento

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.network.response.SearchMedicamentoResponse
import com.codinsa.farmacovigilancia.searchmedicamento.SearchListMedicamentoActivity.Companion.N_REGISTRO

class SearchDetalleMedicamentoActivity : AppCompatActivity() {

    private lateinit var ivImage: ImageView
    private lateinit var tvNombre: TextView
    private lateinit var tvLab: TextView
    private lateinit var tvCPresc: TextView
    private lateinit var tvReceta: TextView
    private lateinit var tvGenerico: TextView
    private lateinit var tvViaAdministracion: TextView
    private lateinit var tvDosis: TextView
    private lateinit var btnFicha: Button
    private lateinit var btnProspecto: Button

    private var urlFicha: String = ""
    private var urlProspecto: String = ""

    private lateinit var viewModelSearch: SearchListMedicamentoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_search_detalle_medicamento)

        viewModelSearch = ViewModelProvider(this)[SearchListMedicamentoViewModel::class.java]

        initUI()

        initListener()

        val nregistro = intent.getStringExtra(N_REGISTRO) ?: ""

        viewModelSearch.getDetallesMedicamento(nregistro)

        observerSearch()

    }

    private fun initUI() {
        ivImage = findViewById(R.id.ivImageSearchDetalleMedicamento)
        tvNombre = findViewById(R.id.tvNombreSearchDetalleMedicamento)
        tvLab = findViewById(R.id.tvLaboratorioSearchDetalleMedicamento)
        tvCPresc = findViewById(R.id.tvCPrescSearchDetalleMedicamento)
        tvReceta = findViewById(R.id.tvRecetaSearchDetalleMedicamento)
        tvGenerico = findViewById(R.id.tvGenericoSearchDetalleMedicamento)
        tvViaAdministracion = findViewById(R.id.tvViaAdministracionSearchDetalleMedicamento)
        tvDosis = findViewById(R.id.tvDosisSearchDetalleMedicamento)

        btnFicha = findViewById(R.id.btnFichaTecnicaSearchDetalleMedicamento)
        btnProspecto = findViewById(R.id.btnProspectoSearchDetalleMedicamento)
    }

    private fun initListener() {

        btnFicha.setOnClickListener {

            if(urlFicha.isNotEmpty()) {

                val intent = Intent()
                intent.setDataAndType(Uri.parse(urlFicha), "application/pdf")
                startActivity(intent)

            } else {
                Toast.makeText(this, "Lo sentimos. No se encontró el documento.", Toast.LENGTH_SHORT).show()
            }

        }

        btnProspecto.setOnClickListener {
            if(urlProspecto.isNotEmpty()) {

                val intent = Intent()
                intent.setDataAndType(Uri.parse(urlProspecto), "application/pdf")
                startActivity(intent)

            } else {
                Toast.makeText(this, "Lo sentimos. No se encontró el documento.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun observerSearch() {
        viewModelSearch.detalleMedicamentoService.observe(this) {
            it?.let {
              mostrarDatos(it[0])
            }
        }
    }

    private fun mostrarDatos(medicamento: SearchMedicamentoResponse) {

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

        tvNombre.text = medicamento.nombre
        tvLab.text = "Laboratorio: ${medicamento.laboratorio}"
        tvCPresc.text = medicamento.cpresc

        if(medicamento.receta) {
            tvReceta.text = "Con receta"
        } else {
            tvReceta.text = "No requiere receta"
        }

        if(medicamento.generico) {
            tvGenerico.text = "Genérico"
        } else {
            tvGenerico.text = "No genérico"
        }

        tvViaAdministracion.text = "Vía administración: " + medicamento.viasAdministracion?.get(0)?.nombre ?: ""
        tvDosis.text = "Dosis: " + medicamento.dosis

        if(medicamento.docs != null) {
            if(medicamento.docs!!.isNotEmpty()) {
                if(medicamento.docs!!.getOrNull(0) != null) {
                    if(medicamento.docs!![0].tipo == 1) {
                        urlFicha = medicamento.docs!![0].url
                    } else if(medicamento.docs!![0].tipo == 2) {
                        urlProspecto = medicamento.docs!![0].url
                    }
                }

                if(medicamento.docs!!.getOrNull(1) != null) {
                    urlProspecto = medicamento.docs!![1].url
                }
            }
        }
    }

}