package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class Usuario : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")
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
        bottom_bar.selectedItemId = R.id.nav_usuario
        bottom_bar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_usuario -> {
                    // No hacer nada si ya está en la actividad Usuario
                    true
                }
                R.id.nav_inicio -> {
                    startActivity(Intent(this, Recetas::class.java))
                    finish() // Cierra la actividad actual
                    true
                }
                R.id.nav_recetas_guardadas -> {
                    startActivity(Intent(this, RecetasGuardadas::class.java))
                    finish() // Cierra la actividad actual
                    true
                }
                else -> false
            }
        }
        // Configura el botón de configuración
        val botonConf = findViewById<ImageButton>(R.id.imageView19)
        botonConf.setOnClickListener {
            try {
                val intent = Intent(this, Configuracion_y_privacidad::class.java)
                startActivity(intent) // Abre la actividad Configuración y Privacidad
            } catch (e: Exception) {
                e.printStackTrace() // Imprime el stack trace del error en Logcat
            }
        }
        // Configura el botón de retroceso
        val botonAtras = findViewById<ImageButton>(R.id.button5)
        botonAtras.setOnClickListener {
            finish() // Cierra la actividad Usuario
        }
    }
}
