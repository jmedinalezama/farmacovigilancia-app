package com.codinsa.farmacovigilancia.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    val usuarioAuthService = MutableLiveData<Usuario>()

    fun cerrarSession() {
        auth.signOut()
    }

    fun getUsuarioAuth(uid: String) {
        firestore
            .collection("usuarios")
            .document(uid)
            .get()
            .addOnSuccessListener {
                if(it != null) {
                    if(it.exists()) {
                        val usuario = it.toObject(Usuario::class.java)
                        usuario?.uid = uid
                        usuarioAuthService.value = usuario
                    }
                }
            }.addOnFailureListener {
                Log.d("usuarioauth", "Error", it)
            }
    }


}