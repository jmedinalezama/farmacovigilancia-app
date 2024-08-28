package com.codinsa.farmacovigilancia.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios_table")
data class Usuario (
    @ColumnInfo(name = "uid")
    val uid: String,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "apellidos")
    val apellidos: String,
    @ColumnInfo(name = "correo")
    val correo: String,
    @ColumnInfo(name = "dni")
    val dni: String,
    @ColumnInfo(name = "imagen")
    val imagen: String,
    @ColumnInfo(name = "tipo")
    val tipo: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}