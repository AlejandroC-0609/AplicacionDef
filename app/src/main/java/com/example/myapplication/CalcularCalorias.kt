package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CalcularCalorias : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calcular_calorias)

        // Configuración de márgenes del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los campos de EditText
        val carnesEditText = findViewById<EditText>(R.id.carnesEditText)
        val verdurasEditText = findViewById<EditText>(R.id.verdurasEditText)
        val frutasEditText = findViewById<EditText>(R.id.frutasEditText)
        val lacteosEditText = findViewById<EditText>(R.id.lacteosEditText)
        val cerealesEditText = findViewById<EditText>(R.id.cerealesEditText)

        // Referencia al botón de calcular
        val calcularButton = findViewById<Button>(R.id.calcularButton)

        // Configurar el listener para el botón
        calcularButton.setOnClickListener {
            // Obtener y convertir los valores ingresados en cada campo, asignando 0 si están vacíos
            val carnesCalories = carnesEditText.text.toString().toIntOrNull() ?: 0
            val verdurasCalories = verdurasEditText.text.toString().toIntOrNull() ?: 0
            val frutasCalories = frutasEditText.text.toString().toIntOrNull() ?: 0
            val lacteosCalories = lacteosEditText.text.toString().toIntOrNull() ?: 0
            val cerealesCalories = cerealesEditText.text.toString().toIntOrNull() ?: 0

            // Calcular el total de calorías
            val totalCalories = carnesCalories + verdurasCalories + frutasCalories + lacteosCalories + cerealesCalories

            // Crear el Intent para abrir la segunda actividad y pasar el total de calorías
            val intent = Intent(this, CaloriasResultados::class.java) // Cambiado a CaloriasResultados
            intent.putExtra("TOTAL_CALORIES", totalCalories)
            startActivity(intent)
        }
    }
}
