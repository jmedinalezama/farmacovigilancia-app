package com.codinsa.farmacovigilancia.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SearchMedicamentoApiService {

    private val URL_BASE = "https://cima.aemps.es/cima/rest/"

    val searchApiService = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(SearchMedicamentoApi::class.java)

}