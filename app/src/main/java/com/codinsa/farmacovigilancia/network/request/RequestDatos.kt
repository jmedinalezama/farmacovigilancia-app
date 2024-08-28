package com.codinsa.farmacovigilancia.network.request

class RequestDatos private constructor(){

    var evento: Evento? = null

    companion object {

        @Volatile
        private var instance: RequestDatos? = null

        fun getInstance() : RequestDatos {
            return instance ?: synchronized(this) {
                instance ?: RequestDatos().also { instance = it }
            }
        }


    }

}