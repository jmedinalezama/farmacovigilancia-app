package com.codinsa.farmacovigilancia.launcher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LauncherViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    val userSessionService = MutableLiveData<Boolean>()

    fun verificarSession() {
        userSessionService.value = (auth.currentUser != null)
    }
}