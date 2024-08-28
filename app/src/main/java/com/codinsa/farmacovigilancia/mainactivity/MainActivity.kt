package com.codinsa.farmacovigilancia.mainactivity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.login.LoginActivity
import com.codinsa.farmacovigilancia.mainactivity.Opciones.Companion.ID_CONSULTAR_MEDICAMENTO
import com.codinsa.farmacovigilancia.mainactivity.Opciones.Companion.ID_INICIAR_SESION
import com.codinsa.farmacovigilancia.nosotros.NosotrosActivity
import com.codinsa.farmacovigilancia.searchmedicamento.SearchListMedicamentoActivity
import com.codinsa.farmacovigilancia.mainactivity.Opciones.Companion.ID_REPORTAR_CASO
import com.codinsa.farmacovigilancia.mainactivity.Opciones.Companion.ID_SOBRE_NOSOTROS

class MainActivity : AppCompatActivity() {

    private lateinit var adapterMain: MainAdapter
    private lateinit var rvItems: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_main)

        initUI()

        adapterMain = MainAdapter() {
            navegarActivity(it)
        }
        rvItems.adapter = adapterMain
        rvItems.layoutManager = GridLayoutManager(this, 2)
        adapterMain.updateListaOpciones(Opciones.listaOpciones)

    }

    private fun initUI() {
        rvItems = findViewById(R.id.rvItemsMainActivity)
    }

    private fun navegarActivity(opcion: Opciones) {

        when(opcion.id) {
            ID_REPORTAR_CASO -> {
                startActivity(Intent(this, ReportChoiceActivity::class.java))
            }
            ID_INICIAR_SESION -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            ID_CONSULTAR_MEDICAMENTO -> {
                startActivity(Intent(this, SearchListMedicamentoActivity::class.java))
            }
            ID_SOBRE_NOSOTROS -> {
                startActivity(Intent(this, NosotrosActivity::class.java))
            }
        }
    }

    override fun onBackPressed() {}

}