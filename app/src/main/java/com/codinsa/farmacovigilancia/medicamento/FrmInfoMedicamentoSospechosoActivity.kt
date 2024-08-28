package com.codinsa.farmacovigilancia.medicamento

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.core.text.isDigitsOnly
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.concomitante.FrmInfoConcomitantesActivity
import com.codinsa.farmacovigilancia.network.request.Medicamento
import com.codinsa.farmacovigilancia.network.request.RequestDatos
import com.codinsa.farmacovigilancia.utils.Utils
import com.google.android.material.textfield.TextInputEditText

class FrmInfoMedicamentoSospechosoActivity : AppCompatActivity() {

    private lateinit var spinnerAccionConMedicamento: Spinner
    private lateinit var etNombreMedicamento: TextInputEditText
    private lateinit var etDosis: TextInputEditText
    private lateinit var etFrecuencia: TextInputEditText
    private lateinit var etViaAdministracion: TextInputEditText
    private lateinit var etFechaInicio: TextInputEditText
    private lateinit var etFechaFin: TextInputEditText
    private lateinit var etMotivoPrescripcion: TextInputEditText
    private lateinit var etNroLote: TextInputEditText

    private lateinit var btnAtras: Button
    private lateinit var btnSiguiente: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_frm_info_medicamento_sospechoso)

        initUI()

        initListener()
    }

    private fun initUI() {

        etNombreMedicamento = findViewById(R.id.etNombreMedicamentoFrmInfoMedicamentoSospechoso)
        etDosis = findViewById(R.id.etDosisFrmInfoMedicamentoSospechoso)
        etFrecuencia = findViewById(R.id.etFrecuenciaFrmInfoMedicamentoSospechoso)
        etViaAdministracion = findViewById(R.id.etViaAdministracionFrmInfoMedicamentoSospechoso)
        etFechaInicio = findViewById(R.id.etFechaInicioFrmInfoMedicamentoSospechoso)
        etFechaFin = findViewById(R.id.etFechaFinFrmInfoMedicamentoSospechoso)
        etMotivoPrescripcion = findViewById(R.id.etMotivoPrescripcionFrmInfoMedicamentoSospechoso)
        etNroLote = findViewById(R.id.etNroLoteFrmInfoMedicamentoSospechoso)
        spinnerAccionConMedicamento = findViewById(R.id.spinnerAccionConMedicamentoFrmInfoMedicamentoSospechoso)

        btnAtras = findViewById(R.id.btnAtrasFrmInfoMedicamentoSospechoso)
        btnSiguiente = findViewById(R.id.btnSiguienteFrmInfoMedicamentoSospechoso)

        fillDataSpinner()

    }

    private fun initListener() {
        btnAtras.setOnClickListener {
            onBackPressed()
        }

        btnSiguiente.setOnClickListener {

            if(validarDatos()) {
                val nombre = etNombreMedicamento.text.toString()
                val dosis = etDosis.text.toString()
                val frecuencia = etFrecuencia.text.toString()
                val viaAdministracion = etViaAdministracion.text.toString()
                val fechaInicio = etFechaInicio.text.toString()
                val fechaFin = etFechaFin.text.toString()
                val motivoPrescripcion = etMotivoPrescripcion.text.toString()
                val nroLote = etNroLote.text.toString()
                val accionConMedicamento = spinnerAccionConMedicamento.selectedItem.toString()

                val data = RequestDatos.getInstance()
                data.evento?.medicamento = Medicamento(nombre, dosis, frecuencia, viaAdministracion,
                    fechaInicio, fechaFin, motivoPrescripcion, nroLote, accionConMedicamento, "")

                startActivity(Intent(this, FrmInfoConcomitantesActivity::class.java))
            }
        }
    }


    private fun fillDataSpinner() {
        spinnerAccionConMedicamento.adapter = Utils.createEntriesSpinner(this, R.array.optionsAccionConMedicamento)
    }

    private fun validarDatos() : Boolean {

        if(
            !validarNombreMedicamento() ||
            !validarDosis() ||
            !validarFrecuencia() ||
            !validarViaAdministracion() ||
            !validarFechaInicio() ||
            !validarFechaFin() ||
            !validarMotivoPrescripcion() ||
            !validarNumeroLote()
        ) {
            return false
        }

        return true
    }

    private fun validarNombreMedicamento() : Boolean {
        val titulo = "Nombre medicamento"

        if(etNombreMedicamento.text.isNullOrEmpty()){
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(etNombreMedicamento.text.toString().isDigitsOnly()) {
            Utils.alerta(this, titulo, "Nombre de medicamento no admitido.")
            return false
        }

        return true
    }

    private fun validarDosis() : Boolean {
        val titulo = "Dosis"

        if(etDosis.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        return true
    }

    private fun validarFrecuencia() : Boolean {
        val titulo = "Frecuencia"

        if(etFrecuencia.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        return true
    }

    private fun validarViaAdministracion() : Boolean {
        val titulo = "Vía administración"

        if(etViaAdministracion.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
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

    private fun validarMotivoPrescripcion() : Boolean {
        val titulo = "Motivo prescripción"

        if(etMotivoPrescripcion.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(etMotivoPrescripcion.text.toString().isDigitsOnly()) {
            Utils.alerta(this, titulo, "Motivo prescripción no admitido.")
            return false
        }

        if(etMotivoPrescripcion.text.toString().length > 100) {
            Utils.alerta(this, titulo, "El motivo de prescripción solo acepta un máximo de 100 caracteres.")
            return false
        }

        return true
    }

    private fun validarNumeroLote() : Boolean {
        val titulo = "Número de lote"

        if(etNroLote.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        return true
    }

}