package com.codinsa.farmacovigilancia.usuario

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.codinsa.farmacovigilancia.database.Usuario
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UsuarioRepository(application)

    fun insertUsuarioWithCoroutines(usuario: Usuario) {
        viewModelScope.launch {
            repository.insertUsuarioWithCoroutines(usuario)
        }
    }

    fun updateUsuarioWithCoroutines(usuario: Usuario) {
        viewModelScope.launch {
            repository.updateUsuarioWithCoroutines(usuario)
        }
    }

    fun deleteUsuarioWithCoroutines(usuario: Usuario) {
        viewModelScope.launch {
            repository.deleteUsuarioWithCoroutines(usuario)
        }
    }

    val usuarios = repository.getUsuarios()

}