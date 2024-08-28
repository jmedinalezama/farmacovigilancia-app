package com.codinsa.farmacovigilancia.admin.reportes

import com.codinsa.farmacovigilancia.utils.FiltrosTypes

data class Filtro (
    var id: Int,
    var nombre: String,
    var isSelected: Boolean
) {

    companion object {

        var listaFiltros = mutableListOf(
            Filtro(1, FiltrosTypes.TODOS.name, true),
            Filtro(2, FiltrosTypes.PROFESIONAL.name, false),
            Filtro(3, FiltrosTypes.PACIENTE.name, false),
            Filtro(4, FiltrosTypes.LEVE.name, false),
            Filtro(5, FiltrosTypes.MODERADO.name, false),
            Filtro(6, FiltrosTypes.SEVERO.name, false)
        )

    }
}