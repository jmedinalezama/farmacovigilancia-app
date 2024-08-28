package com.codinsa.farmacovigilancia.admin.model

data class Paciente(
    var inicialesPaciente: String,
    var sexo: String,
    var fechaNacimiento: String,
    var edad: Int,
    var peso: Double,
    var estatura: Double,
    var embarazadaLactancia: String,
    var departamento: String,
    var comentariosRFV: String
) {
    constructor() : this("", "", "", 0, 0.0, 0.0,
        "", "", "")
}