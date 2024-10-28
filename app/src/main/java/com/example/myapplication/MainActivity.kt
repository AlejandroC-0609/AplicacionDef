package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

var sesion_iniciada = false;

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!sesion_iniciada){
            setContentView(R.layout.activity_inicio1)
            Intent(this, Inicio1::class.java)
                .also { welcomeIntent ->
                    //Launch
                    startActivity(welcomeIntent)
                }
        } else {
            setContentView(R.layout.fragment_recetas1)
            Intent(this, Recetas::class.java)
                .also { welcomeIntent ->
                    //Launch
                    startActivity(welcomeIntent)
                }
        }
        Log.d(TAG, "onCreate")
    }
    override fun onRestoreInstanceState(savedInstanceState:
                                        Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState")
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
    }
    companion object {
        private const val TAG = "MainActivity"
    }

    fun agregarRecetasPorDefault() {
        val db = FirebaseFirestore.getInstance()

        val receta = hashMapOf(
            "nombre" to "Fricassé de Pollo",
            "imagenUrl" to "gs://frugalfeast-3a591.appspot.com/fricase_pollo.jpg",
            "ingredientes" to listOf(
                mapOf("nombre" to "Pollo", "cantidad" to "500g"),
                mapOf("nombre" to "Papas", "cantidad" to "3 unidades")
            ),
            "dificultad" to "Media",
            "tiempo" to "45 minutos",
            "porciones" to 4,
            "preparacion" to "Primero cocine el pollo hasta que esté dorado..."
        )

        db.collection("recetas").add(receta)
            .addOnSuccessListener {
                println("Receta añadida correctamente")
            }
            .addOnFailureListener { e ->
                println("Error al añadir receta: ${e.message}")
            }
    }

}