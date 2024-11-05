package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class EditarRecetas : AppCompatActivity() {
    private lateinit var imgReceta: ImageView
    private lateinit var nombreReceta: EditText
    private lateinit var preparacion: EditText
    private lateinit var ingrediente: EditText
    private lateinit var botonGuardar: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_recetas)

        imgReceta = findViewById(R.id.imageView31) // Imagen de la receta
        nombreReceta = findViewById(R.id.textView5) // Nombre de la receta
        preparacion = findViewById(R.id.textView79) // Preparación de la receta
        ingrediente = findViewById(R.id.textView81) // Ingredientes de la receta
        botonGuardar = findViewById(R.id.button13) // Botón para guardar cambios

        val recipeId = intent.getStringExtra("recipeId") // ID de la receta (asumiendo que es pasado por Intent)
        cargarDatosReceta(recipeId)

        botonGuardar.setOnClickListener {
            guardarCambios(recipeId)
        }
    }

    private fun cargarDatosReceta(recipeId: String?) {
        // Cargar los datos de la receta desde Firebase
        recipeId?.let {
            db.collection("recipes").document(it).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        nombreReceta.setText(document.getString("name"))
                        preparacion.setText(document.getString("preparation"))
                        ingrediente.setText(document.get("ingredients") as? String) // Convertir a texto
                    } else {
                        Toast.makeText(this, "No se encontró la receta", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al cargar la receta: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun guardarCambios(recipeId: String?) {
        // Guardar los cambios en Firebase
        val nombre = nombreReceta.text.toString()
        val prep = preparacion.text.toString()
        val ingredientes = ingrediente.text.toString()

        if (nombre.isEmpty() || prep.isEmpty() || ingredientes.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val recetaActualizada = hashMapOf(
            "name" to nombre,
            "preparation" to prep,
            "ingredients" to ingredientes
        )

        recipeId?.let {
            db.collection("recipes").document(it).set(recetaActualizada)
                .addOnSuccessListener {
                    Toast.makeText(this, "Receta actualizada exitosamente", Toast.LENGTH_SHORT)
                        .show()
                    finish() // Cerrar la actividad después de guardar
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Error al actualizar la receta: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        findViewById<ImageButton>(R.id.button8).setOnClickListener {
            onBackPressed()
        }
    }
}
