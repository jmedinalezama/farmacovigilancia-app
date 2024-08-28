package com.codinsa.farmacovigilancia.resumenreporte

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.evento.EventoViewModel
import com.codinsa.farmacovigilancia.network.request.RequestDatos
import com.codinsa.farmacovigilancia.utils.FormType
import com.codinsa.farmacovigilancia.utils.Utils
import java.time.Year

class FrmResumenReporteActivity : AppCompatActivity() {

    //variables de evento
    private lateinit var tvDescripcionEvento: TextView
    private lateinit var tvFechaInicioEvento: TextView
    private lateinit var tvFechaFinEvento: TextView
    private lateinit var tvEstadoPacienteEvento: TextView
    private lateinit var tvGravedadEvento: TextView

    //variables de paciente
    private lateinit var tvInicialesPaciente: TextView
    private lateinit var tvSexoPaciente: TextView
    private lateinit var tvFechaNacPaciente: TextView
    private lateinit var tvEdadPaciente: TextView
    private lateinit var tvPesoPaciente: TextView
    private lateinit var tvEstaturaPaciente: TextView
    private lateinit var tvEmbarazadaPaciente: TextView
    private lateinit var tvDepartamentoPaciente: TextView

    //variables de medicamento
    private lateinit var tvNombreMedicamento: TextView
    private lateinit var tvDosisMedicamento: TextView
    private lateinit var tvFrecuenciaMedicamento: TextView
    private lateinit var tvViaAdministracionMedicamento: TextView
    private lateinit var tvFechaInicioMedicamento: TextView
    private lateinit var tvFechaFinMedicamento: TextView
    private lateinit var tvMotivoPrescripcionMedicamento: TextView
    private lateinit var tvNroLoteMedicamento: TextView
    private lateinit var tvAccionConMedicamento: TextView

    //variables de reportante
    private lateinit var tvNombreReportante: TextView
    private lateinit var tvTelefonoReportante: TextView
    private lateinit var tvCorreoReportante: TextView
    private lateinit var tvAutorizacionReportante: TextView
    private lateinit var tvOcupacionReportante: TextView

    private lateinit var btnPrevious: Button
    private lateinit var btnSend: Button

    private lateinit var linearLayoutConcomitante: LinearLayout

    private lateinit var viewModelEvento: EventoViewModel
    private var nroControl: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_frm_resumen_reporte)

        viewModelEvento = ViewModelProvider(this)[EventoViewModel::class.java]
        viewModelEvento.obtenerSiguienteNumeroControl()

        initUI()

        initListener()

        observerViewModel()
    }

    private fun initUI() {
        //evento
        tvDescripcionEvento = findViewById(R.id.tvDescripcionEventoFrmResumenReporte)
        tvFechaInicioEvento = findViewById(R.id.tvFechaInicioEventoFrmResumenReporte)
        tvFechaFinEvento = findViewById(R.id.tvFechaFinEventoFrmResumenReporte)
        tvEstadoPacienteEvento = findViewById(R.id.tvEstadoPacienteEventoFrmResumenReporte)
        tvGravedadEvento = findViewById(R.id.tvGravedadEventoFrmResumenReporte)

        //paciente
        tvInicialesPaciente = findViewById(R.id.tvInicialesPacienteFrmResumenReporte)
        tvSexoPaciente = findViewById(R.id.tvSexoPacienteFrmResumenReporte)
        tvFechaNacPaciente = findViewById(R.id.tvFechaNacPacienteFrmResumenReporte)
        tvEdadPaciente = findViewById(R.id.tvEdadPacienteFrmResumenReporte)
        tvPesoPaciente = findViewById(R.id.tvPesoPacienteFrmResumenReporte)
        tvEstaturaPaciente = findViewById(R.id.tvEstaturaPacienteFrmResumenReporte)
        tvEmbarazadaPaciente = findViewById(R.id.tvEmbarazadaPacienteFrmResumenReporte)
        tvDepartamentoPaciente = findViewById(R.id.tvDepartamentoPacienteFrmResumenReporte)

        //medicamento
        tvNombreMedicamento = findViewById(R.id.tvNombreMedicamentoFrmResumenReporte)
        tvDosisMedicamento = findViewById(R.id.tvDosisMedicamentoFrmResumenReporte)
        tvFrecuenciaMedicamento = findViewById(R.id.tvFrecuenciaMedicamentoFrmResumenReporte)
        tvViaAdministracionMedicamento = findViewById(R.id.tvViaAdministracionMedicamentoFrmResumenReporte)
        tvFechaInicioMedicamento = findViewById(R.id.tvFechaInicioMedicamentoFrmResumenReporte)
        tvFechaFinMedicamento = findViewById(R.id.tvFechaFinMedicamentoFrmResumenReporte)
        tvMotivoPrescripcionMedicamento = findViewById(R.id.tvMotivoPrescripcionMedicamentoFrmResumenReporte)
        tvNroLoteMedicamento = findViewById(R.id.tvNroLoteMedicamentoFrmResumenReporte)
        tvAccionConMedicamento = findViewById(R.id.tvAccionConMedicamentoFrmResumenReporte)

        //reportante
        tvNombreReportante = findViewById(R.id.tvNombreReportanteFrmResumenReporte)
        tvTelefonoReportante = findViewById(R.id.tvTelefonoReportanteFrmResumenReporte)
        tvCorreoReportante = findViewById(R.id.tvCorreoReportanteFrmResumenReporte)
        tvAutorizacionReportante = findViewById(R.id.tvAutorizacionReportanteFrmResumenReporte)
        tvOcupacionReportante = findViewById(R.id.tvOcupacionReportanteFrmResumenReporte)

        btnPrevious = findViewById(R.id.btnPreviousFrmResumenReporte)
        btnSend = findViewById(R.id.btnSendFrmResumenReporte)

        linearLayoutConcomitante = findViewById(R.id.linearLayoutConcomitantesFrmResumenReporte)

        mostrarDatos()
    }

    private fun mostrarDatos() {

        val data = RequestDatos.getInstance()
        val evento = data.evento!!
        val paciente = evento.paciente!!
        val medicamento = evento.medicamento!!
        val reportante = evento.reportante!!
        val concomitantes = evento.concomitantes!!

        //evento
        tvDescripcionEvento.text = "Descripción: ${evento.descripcion}"
        tvFechaInicioEvento.text = "F. inicio: ${evento.fechaInicio}"
        tvFechaFinEvento.text = "F. fin: ${evento.fechaFin}"
        tvEstadoPacienteEvento.text = "Estado del paciente: ${evento.estadoPaciente}"
        tvGravedadEvento.text = "Gravedad: ${evento.gravedadEvento}"

        //paciente
        tvInicialesPaciente.text = "Iniciales: ${paciente.inicialesPaciente}"
        tvSexoPaciente.text = "Sexo: ${paciente.sexo}"
        tvFechaNacPaciente.text = "F. nacimiento: ${paciente.fechaNacimiento}"
        tvEdadPaciente.text = "Edad: ${paciente.edad}"
        tvPesoPaciente.text = "Peso: ${paciente.peso}"
        tvEstaturaPaciente.text = "Estatura: ${paciente.estatura}"
        tvEmbarazadaPaciente.text = "Embarazada o lactancia: ${paciente.embarazadaLactancia}"
        tvDepartamentoPaciente.text = "Departamento: ${paciente.departamento}"

        //medicamento
        tvNombreMedicamento.text = "Nombre: ${medicamento.nombre}"
        tvDosisMedicamento.text = "Dosis: ${medicamento.dosis}"
        tvFrecuenciaMedicamento.text = "Frecuencia: ${medicamento.frecuencia}"
        tvViaAdministracionMedicamento.text = "Vía de administración: ${medicamento.viaAdministracion}"
        tvFechaInicioMedicamento.text = "F. inicio: ${medicamento.fechaInicio}"
        tvFechaFinMedicamento.text = "F. fin: ${medicamento.fechaFin}"
        tvMotivoPrescripcionMedicamento.text = "Motivo prescripción: ${medicamento.motivoPrescripcion}"
        tvNroLoteMedicamento.text = "Nro. lote: ${medicamento.numeroLote}"
        tvAccionConMedicamento.text = "Acción con medicamento: ${medicamento.accionConMedicamento}"

        //reportante
        tvNombreReportante.text = "Nombre: ${reportante.nombre}"
        tvTelefonoReportante.text = "Teléfono: ${reportante.telefono}"
        tvCorreoReportante.text = "Correo: ${reportante.correo}"
        tvAutorizacionReportante.text = "Autorización contacto: ${reportante.autorizacion}"

        if(evento.tipoFormulario == FormType.PROFESIONAL.name) {
            tvOcupacionReportante.text = "Profesión: ${reportante.ocupacion}"
        } else {
            tvOcupacionReportante.text = "Relación con paciente: ${reportante.ocupacion}"
        }

        //concomitantes
        if(concomitantes.isNotEmpty()) {
            concomitantes.forEachIndexed { i, concomitante ->
                val textViewConcomitante = TextView(this)
                textViewConcomitante.id = i
                textViewConcomitante.text = "Nombre: ${concomitante.nombre}"
                textViewConcomitante.setTextColor(ContextCompat.getColor(this, R.color.light))
                textViewConcomitante.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                textViewConcomitante.gravity = Gravity.START
                textViewConcomitante.setPadding(Utils.pixelToDp(resources, 10f), Utils.pixelToDp(resources, 5f), 0, 0)

                linearLayoutConcomitante.addView(textViewConcomitante)
            }
        } else {
            val textViewConcomitante = TextView(this)
            textViewConcomitante.text = "No se agregó concomitantes"
            textViewConcomitante.setTextColor(ContextCompat.getColor(this, R.color.light))
            textViewConcomitante.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            textViewConcomitante.gravity = Gravity.START
            textViewConcomitante.setPadding(Utils.pixelToDp(resources, 10f), Utils.pixelToDp(resources, 5f), 0, 0)

            linearLayoutConcomitante.addView(textViewConcomitante)
        }
    }

    private fun initListener() {
        btnPrevious.setOnClickListener {
            onBackPressed()
        }

        btnSend.setOnClickListener {

            val evento = RequestDatos.getInstance().evento!!
            val nroControlGenerado = "COD-${Year.now()}${nroControl.toString().padStart(6, '0')}"
            val eventoCopia = evento.copy(nroControl = nroControlGenerado)

            viewModelEvento.guardarEvento(eventoCopia)

        }
    }

    private fun observerViewModel() {
        viewModelEvento.eventoRegisterService.observe(this) {

            if(it) {
                //Utils.alerta(this, "Envío de reporte", "Se han enviado los datos.")
                startActivity(Intent(this, ConfirmacionEnvioReporteActivity::class.java))
            } else {
                Utils.alerta(this, "Envío de reporte", "Error :(")
            }
        }

        viewModelEvento.generarNroControlService.observe(this) {
            it?.let {
                nroControl = it
            }
        }

    }
}