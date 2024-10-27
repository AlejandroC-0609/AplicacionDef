package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CaloriasResultados : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calorias_resultados)

        // Configuración de márgenes del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recuperar el total de calorías pasado desde la actividad anterior
        val totalCalories = intent.getIntExtra("TOTAL_CALORIES", 0)

        // Mostrar el total de calorías en un TextView
        val totalCaloriesTextView = findViewById<TextView>(R.id.totalCaloriesTextView)
        totalCaloriesTextView.text = "$totalCalories Kcal"
    }
}
