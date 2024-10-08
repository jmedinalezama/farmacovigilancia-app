package com.codinsa.farmacovigilancia.admin.model

data class Evento (
    var id: String,
    var descripcion: String,
    var fechaInicio: String?,
    var fechaFin: String?,
    var estadoPaciente: String,
    var gravedadEvento: String,
    var paciente: Paciente?,
    var medicamento: Medicamento?,
    var reportante: Reportante?,
    var concomitantes: List<Concomitante>?,
    var nombreEvento: String,
    var tipoFormulario: String,
    var fechaCreacion: String?,
    var anioRegistro: String,
    var nroControl: String,
    var estadoEvento: String,
    var comentariosRFV: String
) {

    constructor() : this("", "", "", "", "", "",
        null, null, null, null, "", "",
        "", "", "", "", "")

}