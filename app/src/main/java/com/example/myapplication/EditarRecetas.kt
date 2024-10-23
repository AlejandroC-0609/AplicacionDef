package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditarRecetas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_recetas)

        // Configurar el listener para los insets de la ventana
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView1 = findViewById<ListView>(R.id.recyclerView1)
        val recyclerView2 = findViewById<ListView>(R.id.recyclerView2)

        // Datos para el primer ListView
        val items1 = listOf(
            "Paso 1: Preparar los ingredientes.",
            "Paso 2: Cocinar el pollo a fuego medio.",
            "Paso 3: Añadir las especias.",
            "Paso 4: Revolver bien.",
            "Paso 5: Servir caliente."
        )

        // Datos para el segundo ListView
        val items2 = listOf(
            "Consejo 1: Utiliza ingredientes frescos.",
            "Consejo 2: No olvides sazonar bien.",
            "Consejo 3: Deja reposar antes de servir.",
            "Consejo 4: Acompáñalo con una ensalada.",
            "Consejo 5: Disfruta tu comida."
        )

        // Crear adaptadores y asignarlos a los ListView
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, items1)
        recyclerView1.adapter = adapter1

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, items2)
        recyclerView2.adapter = adapter2
    }
}
