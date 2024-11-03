package com.example.myapplication

import Receta
import RecientementeAdapter
import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class Recetas : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var recientementeAdapter: RecientementeAdapter
    private lateinit var recyclerRecientemente : RecyclerView
    private lateinit var MisRecetasAdapter: RecientementeAdapter
    private lateinit var recyclerMisRecetas : RecyclerView
    private val recetasRecientes = mutableListOf<Receta>()
    private val misRecetas= mutableListOf<Receta>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.fragment_recetas1)

        recyclerRecientemente = findViewById<RecyclerView>(R.id.recyclerRecientemente)
        recientementeAdapter = RecientementeAdapter(recetasRecientes) { receta, view ->
            mostrarMenuContextualRecientes(receta, view)
        }
        recyclerRecientemente.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerRecientemente.adapter = recientementeAdapter

        recyclerMisRecetas = findViewById(R.id.recyclerMisRecetas)
        MisRecetasAdapter = RecientementeAdapter(misRecetas) { receta, view ->
            mostrarMenuContextualMisRecetas(receta, view)
        }
        recyclerMisRecetas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerMisRecetas.adapter = MisRecetasAdapter

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

        verificarRecetasRecientes()
        verificarMisRecetas()

        val calcularCalorias = findViewById<Button>(R.id.btnCalcularCalorias)
       calcularCalorias.setOnClickListener {
            val intent = Intent(this, CalcularCalorias::class.java)
            startActivity(intent)
        }

        val agregar = findViewById<ImageButton>(R.id.agregar)
        agregar.setOnClickListener{
            val intent = Intent(this, AgregarReceta::class.java)
            startActivity(intent)
        }


    }


    // RECETAS RECIENTES
    private fun verificarRecetasRecientes() {
        val textoMensaje = findViewById<TextView>(R.id.textoRecientemente)

        if (recetasRecientes.isEmpty()) {
            textoMensaje.visibility = View.VISIBLE
        } else {
            textoMensaje.visibility = View.GONE
        }
    }

    fun agregarRecetasRecientes(receta: Receta) {
        if (recetasRecientes.size >= 3) {
            recetasRecientes.removeAt(0)
        }
        recetasRecientes.add(receta)
    }

    private fun mostrarMenuContextualRecientes(receta: Receta, view: View) {
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
                    recientementeAdapter.notifyDataSetChanged()
                    verificarRecetasRecientes()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    //MIS RECETAS

    private fun verificarMisRecetas() {
        val textoMensaje = findViewById<TextView>(R.id.textoMisRecetas)

        if (misRecetas.isEmpty()) {
            textoMensaje.visibility = View.VISIBLE
        } else {
            textoMensaje.visibility = View.GONE
        }
    }
    private fun agregarMisRecetas(receta: Receta) {
        if (misRecetas.size >= 3) {
            misRecetas.removeAt(0)
        }
        misRecetas.add(receta)
        MisRecetasAdapter.notifyDataSetChanged()
    }
    private fun mostrarMenuContextualMisRecetas(receta: Receta, view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_receta_2, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.editar -> {
                    true
                }
                R.id.eliminar -> {
                    recetasRecientes.remove(receta)
                    MisRecetasAdapter.notifyDataSetChanged()
                    verificarRecetasRecientes()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }


}
