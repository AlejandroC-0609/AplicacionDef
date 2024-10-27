package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

var sesion_iniciada = false

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar si la sesión está iniciada
        if (!sesion_iniciada) {
            setContentView(R.layout.activity_inicio1)
            startActivity(Intent(this, Inicio1::class.java))
            return
        }

        setContentView(R.layout.activity_main)

        // Cargar el fragmento inicial (Recetas)
        loadFragment(Fragment_recetas1())

        // Configurar BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_recetas -> {
                    loadFragment(Fragment_recetas1())
                    true
                }
                R.id.nav_usuario -> {
                    loadFragment(Fragment_usuario1())
                    true
                }
                R.id.nav_recetas_guardadas -> {
                    loadFragment(RecetasGuardadas1())
                    true
                }
                else -> false
            }
        }

        // Log para verificar el ciclo de vida de la actividad
        Log.d(TAG, "onCreate")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
        return true
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}