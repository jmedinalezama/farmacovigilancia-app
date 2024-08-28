package com.codinsa.farmacovigilancia.network.request

data class Concomitante (
    var nombre: String,
    var fechaInicio: String,
    var fechaFin: String,
    var dosis: String,
    var frecuencia: String,
    var viaAdministracion: String,
    var motivoPrescripcion: String
)