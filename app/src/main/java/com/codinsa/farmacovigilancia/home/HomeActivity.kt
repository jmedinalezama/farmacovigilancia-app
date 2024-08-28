package com.codinsa.farmacovigilancia.home

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.admin.reportes.ListaReportesActivity
import com.codinsa.farmacovigilancia.database.Usuario
import com.codinsa.farmacovigilancia.login.LoginActivity
import com.codinsa.farmacovigilancia.usuario.UsuarioViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var toolBar: MaterialToolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var ivUsuario: ImageView
    private lateinit var tvTipoUsuario: TextView
    private lateinit var tvNombreUsuario: TextView

    private lateinit var viewModelHome: HomeViewModel
    private lateinit var viewModelUsuario: UsuarioViewModel

    private var listaUsuarioAuth = emptyList<Usuario>()
    private var usuarioActivo: Usuario? = null
    private var idActual: Int = 0
    private var sessionActiva: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_home)

        viewModelUsuario = run {
            ViewModelProvider(this)[UsuarioViewModel::class.java]
        }

        viewModelHome = ViewModelProvider(this)[HomeViewModel::class.java]

        initUI()

        initListener()

        observerViewModelUsuariosRoom()

    }

    private fun initUI() {
        toolBar = findViewById(R.id.toolBarHome)
        drawerLayout = findViewById(R.id.drawerLayoutHome)
        navigationView = findViewById(R.id.navigationViewHome)

        val headerView = navigationView.getHeaderView(0)

        ivUsuario = headerView.findViewById(R.id.ivUsuarioDrawerHeader)
        tvTipoUsuario = headerView. findViewById(R.id.tvTipoUsuarioDrawerHeader)
        tvNombreUsuario = headerView.findViewById(R.id.tvNombreUsuarioDrawerHeader)

    }

    private fun initListener() {

        toolBar.setNavigationOnClickListener {
            drawerLayout.open()
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.close()
            navigateToActivity(menuItem.itemId)
            true
        }
    }

    private fun observerViewModelUsuariosRoom() {

        viewModelUsuario.usuarios?.observe(this) {

            if(it != null) {
                if(it.isNotEmpty()) {

                    listaUsuarioAuth = it
                    val usuario = it[0]
                    idActual = usuario.id
                    viewModelHome.getUsuarioAuth(usuario.uid)
                    sessionActiva = true

                } else if(sessionActiva) {

                    listaUsuarioAuth = emptyList()
                    val uid = intent.getStringExtra("UID")!!
                    viewModelHome.getUsuarioAuth(uid)
                    sessionActiva = true
                }

            } else {
                listaUsuarioAuth = emptyList()
                val uid = intent.getStringExtra("UID")!!
                viewModelHome.getUsuarioAuth(uid)
            }

            observerViewModelUsuarioAuth()
        }
    }

    private fun observerViewModelUsuarioAuth() {
        viewModelHome.usuarioAuthService.observe(this) {

            if(it != null) {
                toolBar.title = "Bienvenido, ${it.nombre}"
                tvTipoUsuario.text = it.tipo
                tvNombreUsuario.text = "${it.nombre} ${it.apellidos}"

                val options = RequestOptions().placeholder(R.drawable.ic_user).error(R.drawable.ic_user)
                Glide.with(ivUsuario).setDefaultRequestOptions(options).load(it.imagen).into(ivUsuario)

                usuarioActivo = Usuario(
                    it.uid, it.nombre, it.apellidos, it.correo, it.dni, it.imagen, it.tipo
                )
                usuarioActivo?.id = idActual

                if(listaUsuarioAuth.isEmpty()) {
                    val usuarioRoom = Usuario(
                        it.uid, it.nombre, it.apellidos, it.correo, it.dni, it.imagen, it.tipo
                    )

                    viewModelUsuario.insertUsuarioWithCoroutines(usuarioRoom)
                }
            }
        }
    }

    private fun navigateToActivity(id: Int) {
        when(id) {
            R.id.menuItemReportes -> {
                startActivity(Intent(this, ListaReportesActivity::class.java))
            }
            /*R.id.menuItemAdministracion -> {
                Toast.makeText(applicationContext, "A seleccionado administracion", Toast.LENGTH_SHORT).show()
            }*/
            /*R.id.menuItemGestionUsuarios -> {
                Toast.makeText(applicationContext, "A seleccionado usuarios", Toast.LENGTH_SHORT).show()
            }*/
            R.id.menuItemLogout -> {

                viewModelHome.cerrarSession()
                usuarioActivo?.let { viewModelUsuario.deleteUsuarioWithCoroutines(it) }
                sessionActiva = false
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    override fun onBackPressed() {}
}