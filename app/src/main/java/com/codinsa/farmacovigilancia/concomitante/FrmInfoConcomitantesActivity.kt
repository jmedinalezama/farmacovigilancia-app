package com.codinsa.farmacovigilancia.concomitante

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.network.request.Concomitante
import com.codinsa.farmacovigilancia.network.request.RequestDatos
import com.codinsa.farmacovigilancia.reportante.FrmInfoReportanteActivity
import com.codinsa.farmacovigilancia.utils.Utils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class FrmInfoConcomitantesActivity : AppCompatActivity() {

    private lateinit var fabNuevoConcomitante: FloatingActionButton
    private lateinit var recyclerConcomitante: RecyclerView
    private lateinit var adapterConcomitante: ConcomitanteAdapter

    private lateinit var btnAtras: Button
    private lateinit var btnSiguiente: Button

    private var lstConcomitantes = mutableListOf<Concomitante>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_frm_info_concomitantes)

        initUI()

        initListener()

        adapterConcomitante = ConcomitanteAdapter {eliminarConcomitante(it)}
        adapterConcomitante.updateListConcomitantes(lstConcomitantes)
        recyclerConcomitante.adapter = adapterConcomitante
        recyclerConcomitante.layoutManager = LinearLayoutManager(this)

    }

    private fun initUI() {
        fabNuevoConcomitante = findViewById(R.id.fabNuevoFrmInfoConcomitante)
        recyclerConcomitante = findViewById(R.id.recyclerConcomitantes)

        btnAtras = findViewById(R.id.btnAtrasFrmInfoConcomitante)
        btnSiguiente = findViewById(R.id.btnSiguienteFrmInfoConcomitante)

    }

    private fun initListener() {

        fabNuevoConcomitante.setOnClickListener {
            ingresarConcomitante()
        }

        btnAtras.setOnClickListener {
            onBackPressed()
        }

        btnSiguiente.setOnClickListener {

            val data = RequestDatos.getInstance()
            data.evento?.concomitantes = lstConcomitantes

            startActivity(Intent(this, FrmInfoReportanteActivity::class.java))
        }

    }

    private fun ingresarConcomitante() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_concomitante, null)
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setView(dialogView)
            setTitle("Datos del medicamento")
        }

        val alertDialog = builder.show()

        val etNombre = dialogView.findViewById<TextInputEditText>(R.id.etNombreConcomitanteDialogConcomitante)
        val etFechaInicio = dialogView.findViewById<TextInputEditText>(R.id.etFechaInicioDialogConcomitante)
        val etFechaFin = dialogView.findViewById<TextInputEditText>(R.id.etFechaFinDialogConcomitante)
        val etDosis = dialogView.findViewById<TextInputEditText>(R.id.etDosisDialogConcomitante)
        val etFrecuencia = dialogView.findViewById<TextInputEditText>(R.id.etFrecuenciaDialogConcomitante)
        val etViaAdministracion = dialogView.findViewById<TextInputEditText>(R.id.etViaAdministracionDialogConcomitante)
        val etMotivoPrescripcion = dialogView.findViewById<TextInputEditText>(R.id.etMotivoPrescripcionDialogConcomitante)

        val btnCancelar = dialogView.findViewById<Button>(R.id.btnCancelarDialogConcomitante)
        val btnAgregar = dialogView.findViewById<Button>(R.id.btnAgregarDialogConcomitante)

        btnAgregar.setOnClickListener {
            if(validarDatos(etNombre, etFechaInicio, etFechaFin, etDosis, etFrecuencia, etViaAdministracion, etMotivoPrescripcion)) {

                alertDialog.dismiss()

                val nombre = etNombre.text.toString()
                val fechaInicio = etFechaInicio.text.toString()
                val fechaFin = etFechaFin.text.toString()
                val dosis = etDosis.text.toString()
                val frecuencia = etFrecuencia.text.toString()
                val viaAdministracion = etViaAdministracion.text.toString()
                val motivoPrescripcion = etMotivoPrescripcion.text.toString()

                val concomitante = Concomitante(nombre, fechaInicio, fechaFin, dosis, frecuencia,
                    viaAdministracion, motivoPrescripcion)

                lstConcomitantes.add(concomitante)

                adapterConcomitante.updateListConcomitantes(lstConcomitantes)
            }
        }

        btnCancelar.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    private fun eliminarConcomitante(concomitante: Concomitante) {
        lstConcomitantes.remove(concomitante)
        adapterConcomitante.updateListConcomitantes(lstConcomitantes)
    }

    private fun validarDatos(
        etNombre: TextInputEditText,
        etFechaInicio: TextInputEditText,
        etFechaFin: TextInputEditText,
        etDosis: TextInputEditText,
        etFrecuencia: TextInputEditText,
        etViaAdministracion: TextInputEditText,
        etMotivoPrescripcion: TextInputEditText
    ) : Boolean {

        if(
            !validarNombreMedicamento(etNombre) ||
            !validarFechaInicio(etFechaInicio) ||
            !validarFechaFin(etFechaFin) ||
            !validarDosis(etDosis) ||
            !validarFrecuencia(etFrecuencia) ||
            !validarViaAdministracion(etViaAdministracion) ||
            !validarMotivoPrescripcion(etMotivoPrescripcion)
        ) {
            return false
        }

        return true
    }

    private fun validarNombreMedicamento(input: TextInputEditText) : Boolean {
        val titulo = "Nombre medicamento"

        if(input.text.isNullOrEmpty()){
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(input.text.toString().isDigitsOnly()) {
            Utils.alerta(this, titulo, "Nombre de medicamento no admitido.")
            return false
        }

        return true
    }

    private fun validarFechaInicio(input: TextInputEditText) : Boolean {

        val titulo = "Fecha Inicio"

        if(input.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(!Utils.validarFormatoFecha(input.text.toString())) {
            Utils.alerta(this, titulo, "El formato aceptado es: año/mes/dia")
            return false
        }

        return true
    }

    private fun validarFechaFin(input: TextInputEditText) : Boolean {

        val titulo = "Fecha Fin"

        if(input.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(!Utils.validarFormatoFecha(input.text.toString())) {
            Utils.alerta(this, titulo, "El formato aceptado es: año/mes/dia")
            return false
        }

        return true
    }

    private fun validarDosis(input: TextInputEditText) : Boolean {
        val titulo = "Dosis"

        if(input.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        return true
    }

    private fun validarFrecuencia(input: TextInputEditText) : Boolean {
        val titulo = "Frecuencia"

        if(input.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        return true
    }

    private fun validarViaAdministracion(input: TextInputEditText) : Boolean {
        val titulo = "Vía administración"

        if(input.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        return true
    }

    private fun validarMotivoPrescripcion(input: TextInputEditText) : Boolean {
        val titulo = "Motivo prescripción"

        if(input.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(input.text.toString().isDigitsOnly()) {
            Utils.alerta(this, titulo, "Motivo prescripción no admitido.")
            return false
        }

        if(input.text.toString().length > 100) {
            Utils.alerta(this, titulo, "El motivo de prescripción solo acepta un máximo de 100 caracteres.")
            return false
        }

        return true
    }

}