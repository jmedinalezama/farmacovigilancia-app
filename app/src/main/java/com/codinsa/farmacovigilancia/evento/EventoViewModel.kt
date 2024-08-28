package com.codinsa.farmacovigilancia.evento

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codinsa.farmacovigilancia.network.request.Evento
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class EventoViewModel : ViewModel() {

    private val firestore  = FirebaseFirestore.getInstance()

    private val documentConfig = firestore.collection("configuraciones")
                                            .document("EventoNroControl")

    private val coleccionEventos = firestore.collection("eventos")

    val generarNroControlService = MutableLiveData<Int>()
    val eventoRegisterService = MutableLiveData<Boolean>()

    fun obtenerSiguienteNumeroControl() {
        documentConfig.addSnapshotListener { value, error ->
            if(error != null) {
                generarNroControlService.value = 0

            } else {
                if(value != null) {
                    val nroControl = value["numeroSiguiente"].toString().toInt()
                    generarNroControlService.value = nroControl
                } else {
                    generarNroControlService.value = 0
                }
            }
        }
    }


    fun guardarEvento(evento: Evento) {

        firestore.runTransaction { transaction ->
            transaction.update(documentConfig, "numeroSiguiente", FieldValue.increment(1))

            transaction.set(coleccionEventos.document(), evento)
        }
            .addOnCompleteListener {
                   eventoRegisterService.value = it.isSuccessful
            }
    }
}