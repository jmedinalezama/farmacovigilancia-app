package com.codinsa.farmacovigilancia.nosotros

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.codinsa.farmacovigilancia.mainactivity.MainActivity
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.mapas.MapasCodinsaActivity
import com.codinsa.farmacovigilancia.video.VideoActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class NosotrosActivity : AppCompatActivity() {

    private lateinit var toolBar: MaterialToolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_nosotros)

        initUI()

        initListener()

    }

    private fun initUI() {
        toolBar = findViewById(R.id.toolBarHome)
        drawerLayout = findViewById(R.id.drawerLayoutHome)
        navigationView = findViewById(R.id.navigationViewHome)


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

    private fun navigateToActivity(id: Int) {
        when(id) {
            R.id.menuItemMapa -> {
                startActivity(Intent(this, MapasCodinsaActivity::class.java))
            }
            R.id.menuItemNosotros -> {
                startActivity(Intent(this, NosotrosActivity::class.java))
            }
            R.id.menuItemVideo -> {
                startActivity(Intent(this, VideoActivity::class.java))
            }
            R.id.menuItemLogoutAcercaDe -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}