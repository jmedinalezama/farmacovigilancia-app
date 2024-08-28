package com.codinsa.farmacovigilancia.launcher

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.codinsa.farmacovigilancia.mainactivity.MainActivity
import com.codinsa.farmacovigilancia.R
import com.codinsa.farmacovigilancia.home.HomeActivity

class LauncherActivity : AppCompatActivity() {

    private lateinit var viewModelLauncher: LauncherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.white)
            window.navigationBarColor = getColor(R.color.white)
        }

        setContentView(R.layout.activity_launcher)

        viewModelLauncher = ViewModelProvider(this)[LauncherViewModel::class.java]
        viewModelLauncher.verificarSession()

        Handler(Looper.getMainLooper()).postDelayed({
            observerViewModel()
        }, 1000)
    }

    private fun observerViewModel() {
        viewModelLauncher.userSessionService.observe(this) {
            if(it) {
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

}