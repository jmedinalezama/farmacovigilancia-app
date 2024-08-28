package com.codinsa.farmacovigilancia.mapas

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.codinsa.farmacovigilancia.mainactivity.MainActivity
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.nosotros.NosotrosActivity
import com.codinsa.farmacovigilancia.video.VideoActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MapasCodinsaActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    private lateinit var toolBar: MaterialToolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.first_color)
            window.navigationBarColor = getColor(R.color.first_color)
        }

        setContentView(R.layout.activity_mapas_codinsa)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentMap)
                as SupportMapFragment
        mapFragment.getMapAsync(this)


        initUI()

        initListener()


    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        map.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(this,R.raw.style_json)
        )

        val positionMarker = LatLng( -8.135407848690187, -79.0418024683135)
        map.addMarker(
            MarkerOptions()
                .position(positionMarker)
                .title("Codinsa")
        )
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(positionMarker, 18f),
            4000, null
        )
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isRotateGesturesEnabled = false
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
                Toast.makeText(applicationContext, "A seleccionado Mapa", Toast.LENGTH_SHORT).show()
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