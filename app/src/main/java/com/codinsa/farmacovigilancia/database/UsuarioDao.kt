package com.codinsa.farmacovigilancia.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsuarioDao {

    @Insert
    fun insert(usuario: Usuario)

    @Update
    fun update(usuario: Usuario)

    @Delete
    fun delete(usuario: Usuario)

    @Query("SELECT * FROM usuarios_table")
    fun list(): LiveData<List<Usuario>>


}