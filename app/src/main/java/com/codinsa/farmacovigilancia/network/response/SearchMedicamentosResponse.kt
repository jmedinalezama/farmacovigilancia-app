package com.codinsa.farmacovigilancia.network.response

import com.google.gson.annotations.SerializedName

data class SearchMedicamentosResponse (
    @SerializedName("resultados") var resultados: List<SearchMedicamentoResponse>
)

data class SearchMedicamentoResponse(
    @SerializedName("nregistro") var nregistro: String,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("labtitular") var laboratorio: String,
    @SerializedName("cpresc") var cpresc: String,
    @SerializedName("receta") var receta: Boolean,
    @SerializedName("generico") var generico: Boolean,
    @SerializedName("docs") var docs: List<MedicamentoDocsResponse>?,
    @SerializedName("fotos") var fotos: List<MedicamentoFotosResponse>?,
    @SerializedName("viasAdministracion") var viasAdministracion: List<ViaAdministracionResponse>?,
    @SerializedName("dosis") var dosis: String
)

data class MedicamentoDocsResponse(
    @SerializedName("tipo") var tipo: Int,
    @SerializedName("url") var url: String,
    @SerializedName("fecha") var fecha: Long
)

data class MedicamentoFotosResponse(
    @SerializedName("tipo") var tipo: String,
    @SerializedName("url") var url: String,
    @SerializedName("fecha") var fecha: Long
)

data class ViaAdministracionResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("nombre") var nombre: String
)


