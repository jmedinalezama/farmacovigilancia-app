package com.codinsa.farmacovigilancia.mainactivity

import com.codinsa.farmacovigilancia.R

data class Opciones (
    var id: Int,
    var imagen: Int,
    var texto: String
) {

    companion object {

        const val ID_REPORTAR_CASO = 1
        const val ID_INICIAR_SESION = 2
        const val ID_CONSULTAR_MEDICAMENTO = 3
        const val ID_SOBRE_NOSOTROS = 4

        var listaOpciones = mutableListOf(
            Opciones(ID_REPORTAR_CASO, R.drawable.ic_reporte, "Reportar caso"),
            Opciones(ID_INICIAR_SESION, R.drawable.ic_user, "Iniciar sesión"),
            Opciones(ID_CONSULTAR_MEDICAMENTO, R.drawable.ic_medicamentos, "Consultar medicamentos"),
            Opciones(ID_SOBRE_NOSOTROS, R.drawable.ic_nosotros, "Sobre nosotros")
        )

    }
}