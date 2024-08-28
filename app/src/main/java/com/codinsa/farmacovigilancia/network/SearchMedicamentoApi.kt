package com.codinsa.farmacovigilancia.network

import com.codinsa.farmacovigilancia.network.response.SearchMedicamentosResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchMedicamentoApi {

    @GET("medicamentos")
    fun getMedicamentosPorNombre(@Query("nombre") nombre: String) : Single<SearchMedicamentosResponse>

    @GET("medicamentos")
    fun getDetalleMedicamento(@Query("nregistro") nregistro: String) : Single<SearchMedicamentosResponse>


}