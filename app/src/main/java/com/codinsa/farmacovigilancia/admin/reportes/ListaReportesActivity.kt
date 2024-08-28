package com.codinsa.farmacovigilancia.admin.reportes

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.admin.model.Concomitante
import com.codinsa.farmacovigilancia.admin.model.Evento
import com.codinsa.farmacovigilancia.home.HomeActivity
import com.codinsa.farmacovigilancia.utils.FiltrosTypes
import com.codinsa.farmacovigilancia.utils.FormType
import com.google.android.material.appbar.MaterialToolbar
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ListaReportesActivity : AppCompatActivity() {
    private val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 124

    private lateinit var toolBard: MaterialToolbar

    private lateinit var rvFiltros: RecyclerView
    private lateinit var rvEventos: RecyclerView

    private lateinit var adapterFiltros: FiltrosAdapter
    private lateinit var adapterEventos: EventoReporteAdapter
    private lateinit var adapterDetallesEvento: ItemDetalleEventoAdapter
    private lateinit var adapterDetallesConcomitante: ItemDetalleConcomitanteAdapter

    private lateinit var viewModelEventoReporte: EventoReporteViewModel

    private var listaEventos = emptyList<Evento>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_lista_reportes)

        viewModelEventoReporte = ViewModelProvider(this)[EventoReporteViewModel::class.java]
        viewModelEventoReporte.getEventosFirestore()

        initUI()

        initListener()

        observerViewModel()

        adapterFiltros = FiltrosAdapter() {filtroSeleccionado(it)}
        rvFiltros.adapter = adapterFiltros
        rvFiltros.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapterEventos = EventoReporteAdapter(
            onSelectedDetalles  = { verDetallesEvento(it) },
            onSelectedConcomitantes = { verDetallesConcomitantes(it) },
            onSelectedReporte = { generarVerPDF(it) }
        )
        rvEventos.adapter = adapterEventos
        rvEventos.layoutManager = LinearLayoutManager(this)

    }




    private fun initUI() {
        toolBard = findViewById(R.id.toolBardListaReportes)

        rvFiltros = findViewById(R.id.rvFiltrosListaReportes)
        rvEventos = findViewById(R.id.rvListaReportes)

    }

    private fun initListener() {
        toolBard.setNavigationOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun observerViewModel() {
        viewModelEventoReporte.eventosListService.observe(this) {
            if(it != null) {
                adapterFiltros.updateListaFiltros(Filtro.listaFiltros)
                listaEventos = it
                adapterEventos.updateListaEventos(listaEventos)
            }
        }
    }

    private fun verDetallesEvento(evento: Evento) {

        val listaDetalle = obtenerListaDetallesEvento(evento)

        val dialogView = layoutInflater.inflate(R.layout.dialog_detalles_evento, null)

        val builder = AlertDialog.Builder(this)

        builder.apply {
            setView(dialogView)
        }

        val alertDialog = builder.show()

        val tvNroControl = dialogView.findViewById<TextView>(R.id.tvNroControlDialogDetalleEvento)
        val rvDetallesEvento = dialogView.findViewById<RecyclerView>(R.id.rvDialogDetallesEvento)

        tvNroControl.text = evento.nroControl

        adapterDetallesEvento = ItemDetalleEventoAdapter()
        rvDetallesEvento.adapter = adapterDetallesEvento
        rvDetallesEvento.layoutManager = LinearLayoutManager(this)
        adapterDetallesEvento.updateListaDetalles(listaDetalle)
    }

    private fun verDetallesConcomitantes(evento: Evento) {
        val listaConcomitantes = obtenerListaConcomitantes(evento)

        val dialogView = layoutInflater.inflate(R.layout.dialog_detalles_concomitantes, null)

        val builder = AlertDialog.Builder(this)

        builder.apply {
            setView(dialogView)
        }

        var alertDialog = builder.show()

        val rvDetallesConcomitante = dialogView.findViewById<RecyclerView>(R.id.rvDialogDetallesConcomitantes)

        adapterDetallesConcomitante = ItemDetalleConcomitanteAdapter()
        rvDetallesConcomitante.adapter = adapterDetallesConcomitante
        rvDetallesConcomitante.layoutManager = LinearLayoutManager(this)
        adapterDetallesConcomitante.updateListaConcomitantes(listaConcomitantes)
    }

    private fun obtenerListaDetallesEvento(evento: Evento) : List<ItemDetalleEvento> {

        return mutableListOf(
            ItemDetalleEvento("Código alerta", evento.id), //evento
            ItemDetalleEvento("Descripción", evento.descripcion),
            ItemDetalleEvento("Fecha inicio", evento.fechaInicio ?: ""),
            ItemDetalleEvento("Fecha fin", evento.fechaFin ?: ""),
            ItemDetalleEvento("Estado paciente", evento.estadoPaciente),
            ItemDetalleEvento("Gravedad", evento.gravedadEvento),
            ItemDetalleEvento("Evento", evento.nombreEvento),
            ItemDetalleEvento("Formulario", evento.tipoFormulario),
            ItemDetalleEvento("Fecha creación", evento.fechaCreacion ?: ""),
            ItemDetalleEvento("Año registro", evento.anioRegistro),
            ItemDetalleEvento("Estado evento", evento.estadoEvento),
            ItemDetalleEvento("Iniciales paciente", evento.paciente?.inicialesPaciente ?: ""), //paciente
            ItemDetalleEvento("Sexo", evento.paciente?.sexo ?: ""),
            ItemDetalleEvento("F. nacimiento", evento.paciente?.fechaNacimiento ?: ""),
            ItemDetalleEvento("Edad", evento.paciente?.edad ?: ""),
            ItemDetalleEvento("Peso", evento.paciente?.peso ?: ""),
            ItemDetalleEvento("Estatura", evento.paciente?.estatura ?: ""),
            ItemDetalleEvento("Embarazada o lactancia", evento.paciente?.embarazadaLactancia ?: ""),
            ItemDetalleEvento("Departamento", evento.paciente?.departamento ?: ""),
            ItemDetalleEvento("Medicamento", evento.medicamento?.nombre ?: ""), //medicamento
            ItemDetalleEvento("Dosis", evento.medicamento?.dosis ?: ""),
            ItemDetalleEvento("Frecuencia", evento.medicamento?.frecuencia ?: ""),
            ItemDetalleEvento("Vía administración", evento.medicamento?.viaAdministracion ?: ""),
            ItemDetalleEvento("F. inicio", evento.medicamento?.fechaInicio ?: ""),
            ItemDetalleEvento("F. fin", evento.medicamento?.fechaFin ?: ""),
            ItemDetalleEvento("Motivo prescripción", evento.medicamento?.motivoPrescripcion ?: ""),
            ItemDetalleEvento("Nro lote", evento.medicamento?.numeroLote ?: ""),
            ItemDetalleEvento("Acción con medicamento", evento.medicamento?.accionConMedicamento ?: ""),
            ItemDetalleEvento("Nombre reportante", evento.reportante?.nombre ?: ""), //reportante
            ItemDetalleEvento("Teléfono", evento.reportante?.telefono ?: ""),
            ItemDetalleEvento("Correo", evento.reportante?.correo ?: ""),
            ItemDetalleEvento("Autorización contacto", evento.reportante?.autorizacion ?: ""),
            ItemDetalleEvento("Profesión o relación con paciente", evento.reportante?.ocupacion ?: "")
        )
    }

    private fun obtenerListaConcomitantes(evento: Evento) : List<Concomitante> {

        val listaConcomitantes = mutableListOf<Concomitante>()

        if(!evento.concomitantes.isNullOrEmpty()) {
            for(concomitante in evento.concomitantes!!) {
                listaConcomitantes.add(
                    Concomitante(
                        concomitante.nombre,
                        concomitante.fechaInicio,
                        concomitante.fechaFin,
                        concomitante.dosis,
                        concomitante.frecuencia,
                        concomitante.viaAdministracion,
                        concomitante.motivoPrescripcion)
                )
            }
        }

        return listaConcomitantes
    }

    private fun filtroSeleccionado(filtro: Filtro) {
        Filtro.listaFiltros.forEach {
            it.isSelected = it.id == filtro.id
        }

        adapterFiltros.updateListaFiltros(Filtro.listaFiltros)

        when(filtro.nombre.lowercase()) {
            FiltrosTypes.TODOS.name.lowercase() -> { listarTodos() }
            FiltrosTypes.PROFESIONAL.name.lowercase() -> { filtrarEventoFormularioProfesional() }
            FiltrosTypes.PACIENTE.name.lowercase() -> { filtrarEventoFormularioPaciente() }
            FiltrosTypes.LEVE.name.lowercase() -> { filtrarEventoGravedadLeve() }
            FiltrosTypes.MODERADO.name.lowercase() -> { filtrarEventoGravedadModerado() }
            FiltrosTypes.SEVERO.name.lowercase() -> { filtrarEventoGravedadSevero() }
        }
    }

    private fun listarTodos() {
        adapterEventos.updateListaEventos(listaEventos)
    }

    private fun filtrarEventoFormularioProfesional() {
        val lista = listaEventos.filter {
            it.tipoFormulario.lowercase() == FiltrosTypes.PROFESIONAL.name.lowercase()
        }
        adapterEventos.updateListaEventos(lista)
    }

    private fun filtrarEventoFormularioPaciente() {
        val lista = listaEventos.filter {
            it.tipoFormulario.lowercase() == FiltrosTypes.PACIENTE.name.lowercase()
        }
        adapterEventos.updateListaEventos(lista)
    }

    private fun filtrarEventoGravedadLeve() {
        val lista = listaEventos.filter {
            it.gravedadEvento.lowercase() == FiltrosTypes.LEVE.name.lowercase()
        }
        adapterEventos.updateListaEventos(lista)
    }

    private fun filtrarEventoGravedadModerado() {
        val lista = listaEventos.filter {
            it.gravedadEvento.lowercase() == FiltrosTypes.MODERADO.name.lowercase()
        }
        adapterEventos.updateListaEventos(lista)
    }

    private fun filtrarEventoGravedadSevero() {
        val lista = listaEventos.filter {
            it.gravedadEvento.lowercase() == FiltrosTypes.SEVERO.name.lowercase()
        }
        adapterEventos.updateListaEventos(lista)
    }
    private fun generarVerPDF(evento: Evento) {
        //if (checkPermission()) {
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val pdfFileName = "example_$timestamp.pdf"


            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, pdfFileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                // Agrega más campos según sea necesario
            }
            val resolver = contentResolver
            val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                resolver.insert(
                    MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY),
                    contentValues
                )
            } else {
                TODO("VERSION.SDK_INT < Q")
            }

            uri?.let {
                val outputStream = resolver.openOutputStream(it)
                try {
                    if(outputStream != null){
                        generatePdf(outputStream, evento)
                        Log.e("generado","Todas las operaciones completadas")
                    }
                    showPdf(uri)
                } catch (e: Exception) {
                    // Manejar excepciones específicas o generales
                    e.printStackTrace()
                }

            }
        //}else {
            // Permiso no otorgado, solicitar permisos al usuario
            //requestPermissions()
        //}
    }
    private fun generatePdf(outputStream: OutputStream, evento: Evento) {
        val pdfGenerator = PdfGenerator(outputStream)
        val titulo = "SISTEMA PERUANO DE FARMACOVIGILANCIA Y TECNOVIGILANCIA"
        pdfGenerator.addTitle(titulo)
        val subTitulo = "FORMATO NOTIFICACIÓN DE SOSPECHAS DE REACCIONES ADVERSAS A MEDICAMENTOS U OTROS PRODUCTOS FARMACÉUTICOS POR LOS TITULARES DE\n" +
                "REGISTRO SANITARIO Y DEL CERTIFICADO DE REGISTRO SANITARIO"
        pdfGenerator.addCenteredText(subTitulo)
        val titulotabla = "CONFIDENCIAL"
        pdfGenerator.addCenteredTextSinMargin(titulotabla)
        val listatabla1 = listOf("Nº Notificacion de la empresa","Nº Notificacion del CNAFyT")
        pdfGenerator.addCustomTableRow(listatabla1)
        val titDatosPac = "A. DATOS DEL PACIENTE"
        pdfGenerator.addCustomTableRowSinSeparador(titDatosPac)
        val mitadCuatro = listOf<String>(evento.paciente!!.inicialesPaciente,""+evento.paciente!!.edad,evento.paciente!!.sexo,""+evento.paciente!!.peso,evento.paciente!!.comentariosRFV)
        pdfGenerator.addCustomTableRowMitadCuatro(mitadCuatro)
        val titREACCIONES = "B. REACCIONES ADVERSAS SOSPECHADAS"
        pdfGenerator.addCustomTableRowSinSeparador(titREACCIONES)
        pdfGenerator.addCheckboxTextRow()
        val listagradversa = listOf<String>("Describir la reacción adversa:\n" + evento.descripcion,"Fecha de inicio de RAM:" + evento.fechaInicio + "\n" + "Fecha final de RAM:" + evento.fechaFin)
        val listaboleanChk = listOf<Boolean>( evento.gravedadEvento.equals("Leve"),evento.gravedadEvento.equals("Moderado"),evento.gravedadEvento.equals("Severo"))
        pdfGenerator.addCustomTable(listagradversa,listaboleanChk)

        val titREACCIONES1 = "C. MEDICAMENTO(S) U OTRO(S) PRODUCTO(S) FARMACEUTICO(S) SOSPECHOSO(S) (En el caso de productos biológicos es necesario registrar el nombre comercial,\n" +
                "laboratorio fabricante, número de registro sanitario y número de lote)"
        pdfGenerator.addCustomTableRowSinSeparador(titREACCIONES1)


        val listaCabeceras: List<String> = listOf(
            "Nombre comercial\n" +"y genérico",
            "Fabricante\n" +"y pais",
            "Número\n" +"Lote",
            "Dosis/\n" + "Frecuencia",
            "Via de\n" + "Adm.",
            "Fecha\n" + "inicio",
            "Fecha\n" +"Fin",
            "Motivo de\n" +"prescripción\n" + "o CIE 10")
        val listaFilas: MutableList<List<String>> = mutableListOf()
        val listaData: List<String> = listOf(
            evento.medicamento!!.nombre,
            ".. Peru",
            evento.medicamento!!.numeroLote,
            evento.medicamento!!.dosis + "/" + evento.medicamento!!.frecuencia,
            evento.medicamento!!.viaAdministracion,
            evento.medicamento!!.fechaInicio,
            evento.medicamento!!.fechaFin,
            evento.medicamento!!.motivoPrescripcion)
        listaFilas.add(listaData)
        pdfGenerator.addTablerReporte(listaCabeceras, listaFilas)


        val titREACCIONES2 = "D. MEDICAMENTO Y OTRO PRODUCTO FARMACEUTICO CONCOMITANTE UTILIZADO EN LOS 3 ULTIMOS MESES Excluir medicamente u otro producto farmaceutico\n" +
                "para tratar la reaccion adversa"
        pdfGenerator.addCustomTableRowSinSeparador(titREACCIONES2)


        val listaCabeceras1: List<String> = listOf(
            "Nombre comercial\n" +"y genérico",
            "Dosis/\n" + "Frecuencia",
            "Via de\n" + "Adm.",
            "Fecha\n" + "inicio",
            "Fecha\n" +"Fin",
            "Motivo de\n" +"prescripción"
        )
        val listaFilas1: MutableList<List<String>> = mutableListOf()
        for( concomitante in evento.concomitantes!!){
            val listaData1: List<String> = listOf(
                concomitante.nombre,
                concomitante.dosis + "/" + concomitante.frecuencia,
                concomitante.viaAdministracion,
                concomitante.fechaInicio,
                concomitante.fechaFin,
                concomitante.motivoPrescripcion
            )
            listaFilas1.add(listaData1)
        }
        pdfGenerator.addTablerReporte(listaCabeceras1, listaFilas1)

        pdfGenerator.close();
    }

    private fun showPdf(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        try {
            startActivity(intent)
        } catch (e: Exception) {
            // Manejar la excepción (por ejemplo, si no hay un visor de PDF instalado)
        }

    }


    private fun checkPermission(): Boolean {
        // Verificar si el permiso WRITE_EXTERNAL_STORAGE está otorgado
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
        )
    }

    // Método llamado después de que el usuario responde a la solicitud de permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso WRITE_EXTERNAL_STORAGE otorgado, verificar el siguiente permiso
                    if (checkPermission()) {
                        // Ambos permisos otorgados, realizar la acción correspondiente
                    } else {
                        // Solicitar el permiso faltante
                        requestPermissions()
                    }
                } else {
                    // Permiso WRITE_EXTERNAL_STORAGE denegado, manejar la situación según sea necesario
                }
            }
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso READ_EXTERNAL_STORAGE otorgado, realizar la acción correspondiente
                } else {
                    // Permiso READ_EXTERNAL_STORAGE denegado, manejar la situación según sea necesario

                }
            }
            // Agregar más casos según sea necesario para otros permisos
        }
    }
}