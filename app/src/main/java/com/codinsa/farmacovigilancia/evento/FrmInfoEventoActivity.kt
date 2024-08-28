package com.codinsa.farmacovigilancia.evento

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.network.request.RequestDatos
import com.codinsa.farmacovigilancia.paciente.FrmInfoPacienteActivity
import com.codinsa.farmacovigilancia.utils.Utils
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDate
import java.time.Year

class FrmInfoEventoActivity : AppCompatActivity() {

    private lateinit var spinnerGravedadEvento: Spinner
    private lateinit var spinnerEstadoPaciente: Spinner
    private lateinit var etDescripcion: TextInputEditText
    private lateinit var etFechaInicio: TextInputEditText
    private lateinit var etFechaFin: TextInputEditText

    private lateinit var btnAtras: Button
    private lateinit var btnSiguiente: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_frm_info_evento)

        initUI()

        initListener()

    }

    private fun initUI() {
        spinnerGravedadEvento = findViewById(R.id.spinnerGravedadFrmInfoEvento)
        spinnerEstadoPaciente = findViewById(R.id.spinnerEstadoPacienteFrmInfoEvento)
        etDescripcion = findViewById(R.id.etDescripcionFrmInfoEvento)
        etFechaInicio = findViewById(R.id.etFechaInicioFrmInfoEvento)
        etFechaFin = findViewById(R.id.etFechaFinFrmInfoEvento)

        btnAtras = findViewById(R.id.btnAtrasFrmInfoEvento)
        btnSiguiente = findViewById(R.id.btnSiguienteFrmInfoEvento)

        fillDataSpinner()
    }

    private fun initListener() {
        btnAtras.setOnClickListener {
            onBackPressed()
        }

        btnSiguiente.setOnClickListener {

            if(validarDatos()) {
                val descripcion = etDescripcion.text.toString()
                val fechaInicio = etFechaInicio.text.toString()
                val fechaFin = etFechaFin.text.toString()
                val gravedad = spinnerGravedadEvento.selectedItem.toString()
                val estadoPaciente = spinnerEstadoPaciente.selectedItem.toString()

                val data = RequestDatos.getInstance()
                data.evento?.descripcion = descripcion
                data.evento?.fechaInicio = fechaInicio
                data.evento?.fechaFin = fechaFin
                data.evento?.gravedadEvento = gravedad
                data.evento?.estadoPaciente = estadoPaciente
                data.evento?.nombreEvento = ""
                data.evento?.fechaCreacion = LocalDate.now().toString()
                data.evento?.anioRegistro = Year.now().toString()
                data.evento?.nroControl = ""
                data.evento?.estadoEvento = "En seguimiento"
                data.evento?.comentariosRFV = ""

                startActivity(Intent(this, FrmInfoPacienteActivity::class.java))
            }
        }
    }

    private fun fillDataSpinner() {
        spinnerGravedadEvento.adapter = Utils.createEntriesSpinner(this, R.array.optionsGravedadEvento)
        spinnerEstadoPaciente.adapter = Utils.createEntriesSpinner(this, R.array.optionsEstadoActualPaciente)
    }

    private fun validarDatos() : Boolean {

        if(
            !validarDescripcion() ||
            !validarFechaInicio() ||
            !validarFechaFin()) {
            return false
        }

        return true
    }

    private fun validarDescripcion() : Boolean {

        val titulo = "Descripción"

        if(etDescripcion.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Descripción vacía.")
            return false
        }

        if(etDescripcion.text.toString().length > 100) {
            Utils.alerta(this, titulo, "Se aceptan máximo 100 caracteres.")
            return false
        }

        return true
    }

    private fun validarFechaInicio() : Boolean {

        val titulo = "Fecha Inicio"

        if(etFechaInicio.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(!Utils.validarFormatoFecha(etFechaInicio.text.toString())) {
            Utils.alerta(this, titulo, "El formato aceptado es: año/mes/dia")
            return false
        }

        return true
    }

    private fun validarFechaFin() : Boolean {

        val titulo = "Fecha Fin"

        if(etFechaFin.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(!Utils.validarFormatoFecha(etFechaFin.text.toString())) {
            Utils.alerta(this, titulo, "El formato aceptado es: año/mes/dia")
            return false
        }

        return true
    }




}