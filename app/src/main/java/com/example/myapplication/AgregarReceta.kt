package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AgregarReceta : AppCompatActivity() {

    private lateinit var imgReceta: ImageView
    private lateinit var nombreReceta: EditText
    private lateinit var preparacion: EditText
    private lateinit var ingrediente: EditText
    private val listaIngredientes = mutableListOf<String>()
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_receta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgReceta = findViewById(R.id.imageView32)
        nombreReceta = findViewById(R.id.nombreCampoAgregar)
        preparacion = findViewById(R.id.preparacionCampoAgregar)
        ingrediente = findViewById(R.id.ingrediente1)
        ingrediente = findViewById(R.id.ingrediente2)
        ingrediente = findViewById(R.id.ingrediente3)


        // Botón para seleccionar imagen
        findViewById<Button>(R.id.agregarImagen).setOnClickListener {
            selectImageFromGallery()
        }

        // Botón para agregar ingrediente
        findViewById<Button>(R.id.agregarIngrediente).setOnClickListener {
            val ingredienteTexto = ingrediente.text.toString()
            if (ingredienteTexto.isNotEmpty()) {
                listaIngredientes.add(ingredienteTexto)
                ingrediente.text.clear()
            }
        }

        // Botón para guardar la receta
        findViewById<Button>(R.id.button6).setOnClickListener {
            saveRecipeToFirebase()
        }

        // Botón de "atrás"
        findViewById<ImageButton>(R.id.atras).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {
            imageUri = data?.data
            imgReceta.setImageURI(imageUri)
        }
    }

    private fun saveRecipeToFirebase() {
        val recipeName = nombreReceta.text.toString()
        val preparation = preparacion.text.toString()

        if (recipeName.isEmpty() || preparation.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val storageRef = FirebaseStorage.getInstance().reference.child("recipe_images/${UUID.randomUUID()}")

        // Subir la imagen a Firebase Storage
        imageUri?.let {
            storageRef.putFile(it)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        // Guardar datos en Firestore
                        val recipeData = hashMapOf(
                            "name" to recipeName,
                            "preparation" to preparation,
                            "ingredients" to listaIngredientes,
                            "imageUrl" to uri.toString()
                        )

                        // Reemplaza `firestore.collection("recipes")` con tu lógica de Firestore
                        // firestore.collection("recipes")
                        //     .add(recipeData)
                        //     .addOnSuccessListener {
                        //         Toast.makeText(this, "Receta guardada exitosamente", Toast.LENGTH_SHORT).show()
                        //         finish()
                        //     }
                        //     .addOnFailureListener { e ->
                        //         Toast.makeText(this, "Error al guardar la receta: ${e.message}", Toast.LENGTH_SHORT).show()
                        //     }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al subir la imagen: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }
}
