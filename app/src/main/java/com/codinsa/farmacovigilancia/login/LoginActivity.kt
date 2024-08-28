package com.codinsa.farmacovigilancia.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.codinsa.farmacovigilancia.mainactivity.MainActivity
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.home.HomeActivity
import com.codinsa.farmacovigilancia.utils.Utils
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var btnIngresar: Button
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText

    private lateinit var viewModelLogin: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_login)

        viewModelLogin = ViewModelProvider(this)[LoginViewModel::class.java]

        initUI()

        initListener()

        observerViewModel()

    }

    private fun initUI() {
        btnIngresar = findViewById(R.id.btnIngresarLogin)
        etEmail = findViewById(R.id.etEmailLogin)
        etPassword = findViewById(R.id.etPasswordLogin)
    }

    private fun initListener() {
        btnIngresar.setOnClickListener {

            if(validarDatos()) {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                viewModelLogin.login(email, password)
            }
        }
    }

    private fun observerViewModel() {

        viewModelLogin.userLoginService.observe(this) {login ->
            if(login != null) {
                if(login.isSuccessful) {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("UID", login.uid)
                    startActivity(intent)

                } else {
                    Utils.alerta(this, "Inicio de sesión", "Las credenciales son incorrectas.")
                }
            } else {
               Utils.alerta(this, "Inicio de sesión", "Las credenciales son incorrectas.")
            }
        }
    }

    private fun validarDatos() : Boolean {

        if(!validarEmail() || !validarPassword()) {
            return false
        }

        return true
    }

    private fun validarEmail() : Boolean {

        val titulo = "Email"

        if(etEmail.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Correo vacío.")
            return false
        }

        if(!Utils.validarCorreo(etEmail.text.toString())) {
            Utils.alerta(this, titulo, "Debe ingresar un correo válido.")
            return false
        }

        return true
    }

    private fun validarPassword() : Boolean {

        val titulo = "Contraseña"

        if(etPassword.text.isNullOrEmpty()) {
            Utils.alerta(this, titulo, "Contraseña vacía.")
            return false
        }

        return true
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }

}