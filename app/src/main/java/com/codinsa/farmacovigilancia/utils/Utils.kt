package com.codinsa.farmacovigilancia.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import kotlin.math.roundToInt

class Utils {

    companion object {

        fun pixelToDp(resources: Resources, px: Float) : Int {
            return TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics)
                    .roundToInt()
        }

        fun createEntriesSpinner(context: Context, datos: Int) : ArrayAdapter<CharSequence> {
            val adapter = ArrayAdapter.createFromResource(context, datos, android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            return adapter
        }

        fun alerta(context: Context, title: String, message: String) {
            val builder = AlertDialog.Builder(context)

            with(builder) {
                setTitle(title)
                setMessage(message)
                setPositiveButton("Aceptar", null)

                show()
            }
        }

        fun validarFormatoFecha(fecha: String): Boolean {
            val patron = Regex("^\\d{4}/(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])$")
            return fecha.matches(patron)
        }

        fun validarCorreo(correo: String) : Boolean {
            val patron = Regex("[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}")
            return correo.matches(patron)
        }

        fun validarNumeroConDecimales(texto: String) : Boolean {
            val pattern = Regex("^\\d*\\.?\\d+$")
            return texto.matches(pattern)
        }

    }

}