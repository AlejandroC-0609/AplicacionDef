package com.example.myapplication

import Receta
import RecientementeAdapter
import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.firestore.FirebaseFirestore

class Recetas : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var recientementeAdapter: RecientementeAdapter
    private val recetasRecientes = mutableListOf<Receta>()
    private lateinit var recyclerRecientemente : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.fragment_recetas1)

        recyclerRecientemente = findViewById<RecyclerView>(R.id.recyclerRecientemente)
        recientementeAdapter = RecientementeAdapter(recetasRecientes) { receta, view ->
            mostrarMenuContextual(receta, view)
        }
        recyclerRecientemente.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerRecientemente.adapter = recientementeAdapter

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cargarRecetasFirebase()

        verificarRecetasRecientes()


        val calcularCalorias = findViewById<Button>(R.id.btnCalcularCalorias)
       calcularCalorias.setOnClickListener {
            val intent = Intent(this, CalcularCalorias::class.java)
            startActivity(intent)
        }



    }
    private fun cargarRecetasFirebase() {
        db.collection("datosDefault")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val receta = document.toObject(Receta::class.java)
                    mostrarReceta(receta)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al cargar recetas: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun mostrarReceta(receta: Receta) {
        // Simular que el usuario revisa la receta
        recientementeAdapter.agregarReceta(receta)
        verificarRecetasRecientes()
    }

    private fun verificarRecetasRecientes() {
        val textoMensaje = findViewById<TextView>(R.id.textoRecientemente)

        if (recetasRecientes.isEmpty()) {
            textoMensaje.visibility = View.VISIBLE
        } else {
            textoMensaje.visibility = View.GONE
        }
    }
    private fun mostrarMenuContextual(receta: Receta, view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_receta, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.guardar -> {
                    true
                }
                R.id.eliminar -> {
                    recetasRecientes.remove(receta)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}
