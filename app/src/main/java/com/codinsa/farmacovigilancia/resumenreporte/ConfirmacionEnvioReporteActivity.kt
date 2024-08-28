package com.codinsa.farmacovigilancia.resumenreporte

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.codinsa.farmacovigilancia.mainactivity.MainActivity
import com.codinsa.farmacovigilancia.R

class ConfirmacionEnvioReporteActivity : AppCompatActivity() {

    private lateinit var cvVolverInicio: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_confirmacion_envio_reporte)

        cvVolverInicio = findViewById(R.id.cvVolverInicioConfirmacionEnvioReporte)

        cvVolverInicio.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onBackPressed() {}
}