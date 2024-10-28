package com.example.myapplication

import Receta
import RecientementeAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class Recetas : AppCompatActivity() {
    private lateinit var recientementeAdapter: RecientementeAdapter
    private val recetasList = mutableListOf<Receta>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.fragment_recetas1)

        val recyclerRecientemente = findViewById<RecyclerView>(R.id.recyclerRecientemente)

        recyclerRecientemente.layoutManager = LinearLayoutManager(this)
        recientementeAdapter = RecientementeAdapter(recetasList)
        recyclerRecientemente.adapter = recientementeAdapter

        obtenerRecetas()

        // Configurar los insets de la ventana
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa el botón
        val buttonToCalcularCalorias = findViewById<Button>(R.id.buttonToCalcularCalorias)
        buttonToCalcularCalorias.setOnClickListener {
            val intent = Intent(this, CalcularCalorias::class.java)
            startActivity(intent)
        }

    }
    private fun obtenerRecetas() {
        val db = FirebaseFirestore.getInstance()

        db.collection("recetas").limit(3).get()
            .addOnSuccessListener { result ->
                recetasList.clear()
                for (document in result) {
                    val receta = document.toObject(Receta::class.java)
                    recetasList.add(receta)
                }
                recientementeAdapter.notifyDataSetChanged()
            }
    }
}
