package com.codinsa.farmacovigilancia.usuario

import android.app.Application
import androidx.lifecycle.LiveData
import com.codinsa.farmacovigilancia.database.CodinsaRoomDatabase
import com.codinsa.farmacovigilancia.database.Usuario
import com.codinsa.farmacovigilancia.database.UsuarioDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsuarioRepository(application: Application) {

    private val usuarioDao: UsuarioDao? = CodinsaRoomDatabase.getInstance(application)?.usuarioDao()

    suspend fun insertUsuarioWithCoroutines(usuario: Usuario) {
        withContext(Dispatchers.Default) {
            usuarioDao?.insert(usuario)
        }
    }

    suspend fun updateUsuarioWithCoroutines(usuario: Usuario) {
        withContext(Dispatchers.Default) {
            usuarioDao?.update(usuario)
        }
    }

    suspend fun deleteUsuarioWithCoroutines(usuario: Usuario) {
        withContext(Dispatchers.Default) {
            usuarioDao?.delete(usuario)
        }
    }

    fun getUsuarios() : LiveData<List<Usuario>>? {
        return usuarioDao?.list()
    }

}