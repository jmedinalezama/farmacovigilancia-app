package com.codinsa.farmacovigilancia.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    val userLoginService = MutableLiveData<Login>()

    fun login(email: String, pass: String) {
        validarLogin(email, pass)
    }

    private fun validarLogin(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                task.addOnSuccessListener {
                    if(it.user != null) {
                        val login = Login(it.user!!.uid, task.isSuccessful)
                        userLoginService.value = login
                    } else {
                        userLoginService.value = null
                    }
                }.addOnFailureListener {
                    userLoginService.value = null
                    Log.d("codinsa", "Error credenciales", it)
                }
            }
    }
}