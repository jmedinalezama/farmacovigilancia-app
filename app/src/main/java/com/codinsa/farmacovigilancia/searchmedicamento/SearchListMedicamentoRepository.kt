package com.codinsa.farmacovigilancia.searchmedicamento

import com.codinsa.farmacovigilancia.network.SearchMedicamentoApiService
import com.codinsa.farmacovigilancia.network.response.SearchMedicamentosResponse
import io.reactivex.Single

class SearchListMedicamentoRepository {

    private val api = SearchMedicamentoApiService().searchApiService

    fun getMedicamentosPorNombre(nombre: String): Single<SearchMedicamentosResponse> {
        return api.getMedicamentosPorNombre(nombre)
    }

    fun getDetallesMedicamento(nregistro: String) : Single<SearchMedicamentosResponse> {
        return api.getDetalleMedicamento(nregistro)
    }

}