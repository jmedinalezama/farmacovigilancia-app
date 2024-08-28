package com.codinsa.farmacovigilancia.admin.reportes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codinsa.farmacovigilancia.admin.model.Evento
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.toObject

class EventoReporteViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    val eventosListService = MutableLiveData<List<Evento>>()

    fun getEventosFirestore() {
        firestore.collection("eventos")
            .get()
            .addOnSuccessListener {documentos ->

                val listEvento = mutableListOf<Evento>()

                for (doc in documentos) {
                    val evento = doc.toObject(Evento::class.java)
                    evento.id = doc.id
                    listEvento.add(evento)
                }

                eventosListService.value = listEvento

            }.addOnFailureListener {
                Log.d("codinsa", "Error al leer eventos", it)
                eventosListService.value = emptyList()
            }
    }
}