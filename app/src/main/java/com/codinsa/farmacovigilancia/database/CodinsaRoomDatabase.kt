package com.codinsa.farmacovigilancia.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Usuario::class], version = 1, exportSchema = false)
abstract class CodinsaRoomDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {

        private const val DATABASE_NAME = "farma_database"

        @Volatile
        private var INSTANCE: CodinsaRoomDatabase? = null

        fun getInstance(context: Context) : CodinsaRoomDatabase? {

            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    CodinsaRoomDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }

            return INSTANCE

        }
    }
}