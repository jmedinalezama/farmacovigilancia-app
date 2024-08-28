package com.codinsa.farmacovigilancia.mainactivity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.evento.FrmInfoEventoActivity
import com.codinsa.farmacovigilancia.network.request.Evento
import com.codinsa.farmacovigilancia.network.request.RequestDatos
import com.codinsa.farmacovigilancia.utils.FormType

class ReportChoiceActivity : AppCompatActivity() {

    private lateinit var cvProfesionalSalud: CardView
    private lateinit var cvUsuarioPaciente: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_report_choice)

        initUI()

        initListener()

    }

    private fun initUI(){
        cvProfesionalSalud = findViewById(R.id.cvProfesionalSaludReportChoice)
        cvUsuarioPaciente = findViewById(R.id.cvUsuarioPacienteReportChoice)
    }

    private fun initListener() {

        cvProfesionalSalud.setOnClickListener {

            val data = RequestDatos.getInstance()
            data.evento = Evento("", "", "", "", "",
                null,null, null, null, "",
                "", "", "", "", "", "")

            data.evento?.tipoFormulario = FormType.PROFESIONAL.name

            startActivity(Intent(this, FrmInfoEventoActivity::class.java))
        }

        cvUsuarioPaciente.setOnClickListener {

            val data = RequestDatos.getInstance()
            data.evento = Evento("", "", "", "", "",
                null,null, null, null, "",
                "", "", "", "", "", "")

            data.evento?.tipoFormulario = FormType.PACIENTE.name

            startActivity(Intent(this, FrmInfoEventoActivity::class.java))
        }
    }
}