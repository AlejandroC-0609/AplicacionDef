package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class Usuario : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_usuario)

        // Configurar los insets de la ventana
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recibe las calorías del Intent
        val totalCaloriesToday = intent.getIntExtra("TOTAL_CALORIES_TODAY", 0)
        val totalCaloriesWeek = intent.getIntExtra("TOTAL_CALORIES_WEEK", 0)

        // Asigna los valores a los TextViews en el layout
        findViewById<TextView>(R.id.caloriasDiariasTextView).text = "$totalCaloriesToday kcal"
        findViewById<TextView>(R.id.caloriasSemanalesTextView).text = "$totalCaloriesWeek kcal"

        // Configura la barra de navegación inferior
        val bottom_bar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom_bar.selectedItemId = R.id.nav_inicio
        bottom_bar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_usuario -> {
                    startActivity(Intent(this, Usuario::class.java))
                    true
                }
                R.id.nav_inicio -> {
                    startActivity(Intent(this, Recetas::class.java))
                    true
                }
                R.id.nav_recetas_guardadas -> {
                    startActivity(Intent(this, RecetasGuardadas::class.java))
                    true
                }
                else -> false
            }
            }
        }
}