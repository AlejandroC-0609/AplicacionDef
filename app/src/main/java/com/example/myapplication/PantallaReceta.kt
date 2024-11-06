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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PantallaReceta : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
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
                        findViewById<TextView>(R.id.recetaIngredientes).text =
                            recetaIngredientes.joinToString("\n") { "• $it" }
                    }
                    findViewById<TextView>(R.id.recetaTiempo).text = recetaTiempo
                    findViewById<TextView>(R.id.recetaDificultad).text = recetaDificultad
                    findViewById<TextView>(R.id.recetaCalorias).text = recetaCalorias
                    Glide.with(this).load(recetaImagenUrl).into(findViewById(R.id.recetaImagen))

                    val recetaData = hashMapOf(
                        "recetaId" to recetaId,
                        "nombreReceta" to recetaNombre,
                        "dificultad" to recetaDificultad,
                        "porciones" to recetaPorciones,
                        "tiempoPreparacion" to recetaTiempo,
                        "preparacion" to recetaPreparacion,
                        "ingredientes" to recetaIngredientes,
                        "imagenUrl" to recetaImagenUrl,
                        "calorias" to recetaCalorias
                    )

                        val userId =
                            FirebaseAuth.getInstance().currentUser?.uid ?: return@addOnSuccessListener
                        db.collection("usuarios")
                            .document(userId)
                            .collection("recetasRecientes")
                            .document(recetaId.toString())  // Usamos el recetaId como el ID del documento
                            .set(recetaData)
                            .addOnSuccessListener {
                                // Receta agregada correctamente
                                println("Receta agregada a la colección 'recetasRecientes'")
                            }
                            .addOnFailureListener { e ->
                                // Error al subir la receta
                                println("Error al agregar receta reciente: $e")
                            }
                    }else {
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

}
