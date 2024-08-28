package com.codinsa.farmacovigilancia.admin.model

data class Reportante(
    var nombre: String,
    var telefono: String,
    var correo: String,
    var autorizacion: String,
    var ocupacion: String,
    var comentariosRFV: String
) {
    constructor() : this("", "", "", "", "", "")
}