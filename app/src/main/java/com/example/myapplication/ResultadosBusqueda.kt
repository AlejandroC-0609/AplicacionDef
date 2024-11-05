package com.example.myapplication

import Receta
import android.content.Intent
import android.widget.Toast
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ResultadosBusqueda : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var recyclerBusqueda: RecyclerView
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resultados_busqueda)

        recyclerBusqueda = findViewById(R.id.recyclerResultados)
        recyclerBusqueda.layoutManager = LinearLayoutManager(this)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val searchView = findViewById<SearchView>(R.id.buscarRecetaBar)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    buscarRecetas(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })

        val query = intent.getStringExtra("query")
        if (!query.isNullOrEmpty()) {
            searchView.setQuery(query, false)
            buscarRecetas(query)
        }
    }
    private fun buscarRecetas(query: String) {
        db.collection("datosDefault")
            .whereGreaterThanOrEqualTo("nombreReceta", query)
            .whereLessThanOrEqualTo("nombreReceta", query + "\uf8ff")
            .get()
            .addOnSuccessListener { documents ->
                val recetasEncontradas = mutableListOf<Receta>()
                for (document in documents) {
                    val receta = document.toObject(Receta::class.java)
                    recetasEncontradas.add(receta)
                }
                mostrarResultadosBusqueda(recetasEncontradas)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al buscar recetas", Toast.LENGTH_SHORT).show()
            }
    }
    private fun mostrarResultadosBusqueda(recetasEncontradas: List<Receta>) {
        if (recetasEncontradas.isEmpty()) {
            Toast.makeText(this, "No se encontraron recetas", Toast.LENGTH_SHORT).show()
            return
        }

        val adapter = BusquedaAdapter(recetasEncontradas) { receta, _ ->
            val intent = Intent(this, PantallaReceta::class.java)
            intent.putExtra("RECETA_ID", receta.recetaId)
            startActivity(intent)
        }
        recyclerBusqueda.adapter = adapter
    }
}