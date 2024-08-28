package com.codinsa.farmacovigilancia.home

data class Usuario (
    var uid: String,
    var nombre: String,
    var apellidos: String,
    var correo: String,
    var dni: String,
    var imagen: String,
    var tipo: String
) {
    constructor() : this("", "", "", "","", "", "")
}