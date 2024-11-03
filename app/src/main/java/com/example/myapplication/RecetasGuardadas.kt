package com.example.myapplication

import com.example.myapplication.Usuario
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class RecetasGuardadas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recetas_guardadas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bottom_bar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom_bar.selectedItemId = R.id.nav_inicio
        bottom_bar.setOnItemSelectedListener() { item ->
            when(item.itemId) {
                R.id.nav_usuario -> {
                    val intent = Intent(this, Usuario::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_inicio -> {
                    val intent = Intent(this, Recetas::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_recetas_guardadas ->{
                    val intent = Intent(this, RecetasGuardadas::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}