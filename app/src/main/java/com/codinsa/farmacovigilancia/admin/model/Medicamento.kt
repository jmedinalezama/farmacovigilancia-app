package com.codinsa.farmacovigilancia.admin.model

data class Medicamento(
    var nombre: String,
    var dosis: String,
    var frecuencia: String,
    var viaAdministracion: String,
    var fechaInicio: String,
    var fechaFin: String,
    var motivoPrescripcion: String,
    var numeroLote: String,
    var accionConMedicamento: String,
    var comentariosRFV: String
) {
    constructor() : this("", "", "", "", "", "",
        "", "", "", "")
}
