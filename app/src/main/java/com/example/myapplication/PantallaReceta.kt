package com.example.myapplication

import Receta
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class PantallaReceta : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var recetaId: Number
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_receta)

        val recetaId = intent.getIntExtra("RECETA_ID", -1)
        if (recetaId != -1) {
            cargarReceta(recetaId)
        } else {
            mostrarMensajeError()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val atras = findViewById<ImageButton>(R.id.atras)
        atras.setOnClickListener {
            finish() // Close the activity to return to previous screen

        }
    }

    private fun cargarReceta(recetaId: Int) {
        db.collection("datosDefault").document(recetaId.toString()).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val recetaNombre = document.getString("nombreReceta")
                    val recetaDificultad = document.getString("dificultad")
                    val recetaPorciones = document.getString("¨porciones")
                    val recetaTiempo = document.getString("tiempoPreparacion")
                    val recetaPreparacion = document.getString("¨preparacion")
                    val recetaIngredientes = document.get("ingredientes") as? List<*>
                    val recetaImagenUrl = document.getString("imagenUrl")
                    val recetaCalorias = document.getString("calorias")

                    findViewById<TextView>(R.id.nombreReceta).text = recetaNombre
                    findViewById<TextView>(R.id.recetaPreparacion).text = recetaPreparacion
                    findViewById<TextView>(R.id.recetaPorciones).text = recetaPorciones
                    if (recetaIngredientes != null) {
                        findViewById<TextView>(R.id.recetaIngredientes).text =  recetaIngredientes.joinToString("\n") { "• $it" }
                    }
                    findViewById<TextView>(R.id.recetaTiempo).text = recetaTiempo
                    findViewById<TextView>(R.id.recetaDificultad).text = recetaDificultad
                    findViewById<TextView>(R.id.recetaCalorias).text = recetaCalorias
                    Glide.with(this).load(recetaImagenUrl).into(findViewById(R.id.recetaImagen))

                    agregarRecetaRecientementeVista(recetaId)
                } else {
                    mostrarMensajeError()
                }
            }
            .addOnFailureListener {
                mostrarMensajeError()
            }
    }

    private fun mostrarMensajeError() {
        findViewById<TextView>(R.id.nombreReceta).text =
            getString(R.string.error_al_cargar_la_receta)
    }

    private fun agregarRecetaRecientementeVista(recetaId: Int) {
        val receta = Receta(recetaId = recetaId)
        (applicationContext as? Recetas)?.agregarRecetasRecientes(receta)
    }
}
