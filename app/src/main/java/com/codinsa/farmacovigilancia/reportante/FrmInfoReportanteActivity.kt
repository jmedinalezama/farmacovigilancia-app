package com.codinsa.farmacovigilancia.reportante

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.text.isDigitsOnly
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.network.request.Reportante
import com.codinsa.farmacovigilancia.network.request.RequestDatos
import com.codinsa.farmacovigilancia.resumenreporte.FrmResumenReporteActivity
import com.codinsa.farmacovigilancia.utils.FormType
import com.codinsa.farmacovigilancia.utils.Utils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FrmInfoReportanteActivity : AppCompatActivity() {

    private lateinit var etNombreReportante: TextInputEditText
    private lateinit var etTelefono: TextInputEditText
    private lateinit var etCorreo: TextInputEditText
    private lateinit var etOcupacion: TextInputEditText
    private lateinit var rbGroupAutorizacion: RadioGroup
    private lateinit var rbSi: RadioButton
    private lateinit var rbNo: RadioButton
    private lateinit var chkTerminosLegales: CheckBox

    private lateinit var etlNombreReportante: TextInputLayout
    private lateinit var etlTelefono: TextInputLayout
    private lateinit var etlCorreo: TextInputLayout
    private lateinit var etlOcupacion: TextInputLayout

    private lateinit var btnAtras: Button
    private lateinit var btnSiguiente: Button

    private var autorizacion: String? = null
    private var datosValidados: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_frm_info_reportante)

        initUI()

        initListener()
    }

    private fun initUI() {

        etNombreReportante = findViewById(R.id.etNombreFrmInfoReportante)
        etTelefono = findViewById(R.id.etTelefonoFrmInfoReportante)
        etCorreo = findViewById(R.id.etCorreoFrmInfoReportante)
        etOcupacion = findViewById(R.id.etOcupacionFrmInfoReportante)
        rbGroupAutorizacion = findViewById(R.id.rbGroupFrmInfoReportante)
        rbSi = findViewById(R.id.rbSiFrmInfoReportante)
        rbNo = findViewById(R.id.rbNoFrmInfoReportante)
        chkTerminosLegales = findViewById(R.id.chkTerminosLegales)

        etlNombreReportante = findViewById(R.id.etlNombreFrmInfoReportante)
        etlTelefono = findViewById(R.id.etlTelefonoFrmInfoReportante)
        etlCorreo = findViewById(R.id.etlCorreoFrmInfoReportante)
        etlOcupacion = findViewById(R.id.etlOcupacionFrmInfoReportante)

        btnAtras = findViewById(R.id.btnAtrasFrmInfoReportante)
        btnSiguiente = findViewById(R.id.btnSiguienteFrmInfoReportante)

        validarTipoEntradaOcupacion()
    }

    private fun initListener() {

        btnAtras.setOnClickListener {
            onBackPressed()
        }

        btnSiguiente.setOnClickListener {

            if(validarAutorizacion()) {

                val data = RequestDatos.getInstance()

                if(autorizacion == getString(R.string.rbtnInfoReportanteAutorizacionSi)) {
                    if(validarDatos()) {

                        val nombre = etNombreReportante.text.toString()
                        val telefono = etTelefono.text.toString()
                        val correo = etCorreo.text.toString()
                        val ocupacion = etOcupacion.text.toString()

                        data.evento?.reportante = Reportante(nombre, telefono, correo, autorizacion!!, ocupacion, "")

                        datosValidados = true

                    } else {
                        datosValidados = false

                    }
                } else {
                    data.evento?.reportante = Reportante("", "", "", autorizacion!!, "", "")
                    datosValidados = true
                }

                if(datosValidados) {
                    if(!chkTerminosLegales.isChecked) {
                        Utils.alerta(this, "Términos legales", "Debe aceptar términos legales.")
                    } else {
                        startActivity(Intent(this, FrmResumenReporteActivity::class.java))
                    }
                }
            }
        }

        rbGroupAutorizacion.setOnCheckedChangeListener { _, _ ->
            if(rbSi.isChecked) {
                autorizacion = getString(R.string.rbtnInfoReportanteAutorizacionSi)
                mostrarInputs()
            } else if(rbNo.isChecked) {
                autorizacion = getString(R.string.rbtnInfoReportanteAutorizacionNo)
                ocultarInputs()
            }
        }

    }

    private fun validarTipoEntradaOcupacion() {
        val data = RequestDatos.getInstance()

        val etLayoutOcupacion = findViewById<TextInputLayout>(R.id.etlOcupacionFrmInfoReportante)

        if(data.evento?.tipoFormulario == FormType.PROFESIONAL.name) {
            etLayoutOcupacion.hint = getString(R.string.etInfoReportanteProfesion)
        } else {
            etLayoutOcupacion.hint = getString(R.string.etInfoReportanteRelacionConPaciente)
        }
    }

    private fun mostrarInputs() {
        etlNombreReportante.visibility = View.VISIBLE
        etlTelefono.visibility = View.VISIBLE
        etlCorreo.visibility = View.VISIBLE
        etlOcupacion.visibility = View.VISIBLE
    }

    private fun ocultarInputs() {
        etlNombreReportante.visibility = View.GONE
        etlTelefono.visibility = View.GONE
        etlCorreo.visibility = View.GONE
        etlOcupacion.visibility = View.GONE
    }

    private fun validarAutorizacion() : Boolean {
        if(autorizacion == null) {
            Utils.alerta(this, "Autorización", "Debe seleccionar si desea ser contactado(a).")
            return false
        }
        return true
    }

    private fun validarDatos() : Boolean {
        if(
            !validarNombre() ||
            !validarTelefono() ||
            !validarCorreo() ||
            !validarOcupacion()
        ) {
            return false
        }

        return true
    }

    private fun validarNombre() : Boolean {

        val titulo = "Nombre reportante"

        if(etNombreReportante.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(etNombreReportante.text.toString().isDigitsOnly()) {
            Utils.alerta(this, titulo, "El nombre solo acepta letras.")
            return false
        }

        return true
    }

    private fun validarTelefono() : Boolean {
        val titulo = "Teléfono"

        if(etTelefono.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        return true
    }

    private fun validarCorreo() : Boolean {
        val titulo = "Correo"

        if(etCorreo.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(!Utils.validarCorreo(etCorreo.text.toString())) {
            Utils.alerta(this, titulo, "Ingrese un correo con formato válido.")
            return false
        }

        return true
    }

    private fun validarOcupacion() : Boolean {
        val data = RequestDatos.getInstance()
        var titulo = ""

        titulo = if(data.evento?.tipoFormulario == FormType.PROFESIONAL.name) {
            "Profesión"
        } else {
            "Relación con paciente"
        }

        if(etOcupacion.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(etOcupacion.text.toString().isDigitsOnly()) {
            Utils.alerta(this, titulo, "Solo se aceptan letras.")
            return false
        }

        return true
    }


}