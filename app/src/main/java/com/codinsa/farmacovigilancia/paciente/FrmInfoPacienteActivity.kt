package com.codinsa.farmacovigilancia.paciente

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.core.text.isDigitsOnly
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.medicamento.FrmInfoMedicamentoSospechosoActivity
import com.codinsa.farmacovigilancia.network.request.Paciente
import com.codinsa.farmacovigilancia.network.request.RequestDatos
import com.codinsa.farmacovigilancia.utils.Utils
import com.google.android.material.textfield.TextInputEditText

class FrmInfoPacienteActivity : AppCompatActivity() {

    private lateinit var spinnerDepartamento: Spinner
    private lateinit var spinnerEmbarazada: Spinner
    private lateinit var rbGroupSexo: RadioGroup
    private lateinit var rbHombre: RadioButton
    private lateinit var rbMujer: RadioButton
    private lateinit var etIniciales: TextInputEditText
    private lateinit var etFechaNac: TextInputEditText
    private lateinit var etEdad: TextInputEditText
    private lateinit var etPeso: TextInputEditText
    private lateinit var etAltura: TextInputEditText

    private lateinit var btnAtras: Button
    private lateinit var btnSiguiente: Button

    private var sexo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_frm_info_paciente)

        initUI()

        initListener()
    }

    private fun initUI() {
        spinnerDepartamento = findViewById(R.id.spinnerDepartamentoFrmInfoPaciente)
        spinnerEmbarazada = findViewById(R.id.spinnerEmbarazadaFrmInfoPaciente)
        rbGroupSexo = findViewById(R.id.rbGroupFrmInfoPaciente)
        rbHombre = findViewById(R.id.rbHombreFrmInfoPaciente)
        rbMujer = findViewById(R.id.rbMujerFrmInfoPaciente)
        etIniciales = findViewById(R.id.etInicialesFrmInfoPaciente)
        etFechaNac = findViewById(R.id.etFechaNacimientoFrmInfoPaciente)
        etEdad = findViewById(R.id.etEdadFrmInfoPaciente)
        etPeso = findViewById(R.id.etPesoFrmInfoPaciente)
        etAltura = findViewById(R.id.etAlturaFrmInfoPaciente)

        btnAtras = findViewById(R.id.btnAtrasFrmInfoPaciente)
        btnSiguiente = findViewById(R.id.btnSiguienteFrmInfoPaciente)

        fillDataSpinner()
    }

    private fun initListener() {
        btnAtras.setOnClickListener {
            onBackPressed()
        }

        btnSiguiente.setOnClickListener {
            if(validarDatos()) {
                val departamento = spinnerDepartamento.selectedItem.toString()
                val sexo = findViewById<RadioButton>(rbGroupSexo.checkedRadioButtonId).text.toString()
                val iniciales = etIniciales.text?.toString() ?: ""
                val fechaNacimiento = etFechaNac.text.toString()
                val edad = etEdad.text.toString().toInt()
                val peso = etPeso.text.toString().toDouble()
                val altura = etAltura.text.toString().toDouble()
                val embarazadaLactancia = spinnerEmbarazada.selectedItem.toString()

                val data = RequestDatos.getInstance()
                data.evento?.paciente = Paciente(iniciales, sexo, fechaNacimiento, edad, peso, altura, embarazadaLactancia, departamento, "")

                startActivity(Intent(this, FrmInfoMedicamentoSospechosoActivity::class.java))
            }
        }

        rbGroupSexo.setOnCheckedChangeListener { _, _ ->
            if(rbHombre.isChecked) {
                sexo = getString(R.string.rbtnInfoPacienteSexoHombre)
            } else if (rbMujer.isChecked) {
                sexo = getString(R.string.rbtnInfoPacienteSexoMujer)
            }
        }
    }

    private fun fillDataSpinner() {
        spinnerDepartamento.adapter = Utils.createEntriesSpinner(this, R.array.arrayDepartamentos)
        spinnerEmbarazada.adapter = Utils.createEntriesSpinner(this, R.array.optionsEmbarazada)
    }

    private fun validarDatos(): Boolean {

        if(!validarIniciales() ||
            !validarSexo() ||
            !validarFechaNacimiento() ||
            !validarEdad() ||
            !validarPeso() ||
            !validarAltura()) {
            return false
        }

        return true
    }

    private fun validarIniciales() : Boolean {

        val titulo = "Iniciales"

        if(!etIniciales.text.isNullOrEmpty()) {
            if(etIniciales.text.toString().isDigitsOnly()) {
                Utils.alerta(this, titulo, "La iniciales solo aceptan letras.")
                return false
            }
        }

        return true
    }

    private fun validarSexo() : Boolean {
        if(sexo == null) {
            Utils.alerta(this, "Sexo de paciente", "Debe elegir un sexo.")
            return false
        }
        return true
    }

    private fun validarFechaNacimiento() : Boolean {
        val titulo = "Fecha nacimiento"

        if(etFechaNac.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(!Utils.validarFormatoFecha(etFechaNac.text.toString())) {
            Utils.alerta(this, titulo,
                "La fecha de nacimiento no tiene el formato correcto: año/mes/día")
            return false
        }

        return true
    }

    private fun validarEdad() : Boolean {

        val titulo = "Edad"

        if(etEdad.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(!etEdad.text.toString().isDigitsOnly()) {
            Utils.alerta(this, titulo, "La edad solo acepta números.")
            return false
        }

        if(etEdad.text.toString().toInt() < 1 || etEdad.text.toString().toInt() > 100) {
            Utils.alerta(this, titulo, "Rango de edad aceptable: 1 - 100.")
            return false
        }

        return true
    }

    private fun validarPeso() : Boolean {

        val titulo = "Peso"

        if(etPeso.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(!Utils.validarNumeroConDecimales(etPeso.text.toString())) {
            Utils.alerta(this, titulo, "El peso solo acepta números.")
            return false
        }

        if(etPeso.text.toString().toDouble() < 1 || etPeso.text.toString().toDouble() > 200) {
            Utils.alerta(this, titulo, "Rango de peso aceptable: 1 - 200.")
            return false
        }

        return true
    }

    private fun validarAltura() : Boolean {

        val titulo = "Altura"

        if(etAltura.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Campo vacío.")
            return false
        }

        if(!Utils.validarNumeroConDecimales(etAltura.text.toString())) {
            Utils.alerta(this, titulo, "La altura solo acepta números.")
            return false
        }

        if(etAltura.text.toString().toDouble() < 0 || etAltura.text.toString().toDouble() > 2.5) {
            Utils.alerta(this, titulo, "Rango de altura aceptable: 0 - 2.5")
            return false
        }

        return true
    }



}