package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AgregarReceta : AppCompatActivity() {

    private lateinit var imgReceta: ImageView
    private lateinit var nombreReceta: EditText
    private lateinit var preparacion: EditText
    private lateinit var ingrediente: EditText
    private val listaIngredientes = mutableListOf<String>()
    private var imageUrl: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_receta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
    /*
        imgRecipe = findViewById(R.id.imgRecipe)
        edtRecipeName = findViewById(R.id.edtRecipeName)
        edtPreparation = findViewById(R.id.edtPreparation)
        edtIngredient = findViewById(R.id.edtIngredient)

        // Botón para seleccionar imagen
        findViewById<Button>(R.id.btnAddImage).setOnClickListener {
            selectImageFromGallery()
        }

        // Botón para agregar ingrediente
        findViewById<Button>(R.id.btnAddIngredient).setOnClickListener {
            val ingredient = edtIngredient.text.toString()
            if (ingredient.isNotEmpty()) {
                ingredientList.add(ingredient)
                edtIngredient.text.clear()
            }
        }

        // Botón para guardar la receta
        findViewById<Button>(R.id.btnSaveRecipe).setOnClickListener {
            saveRecipeToFirebase()
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
            imgRecipe.setImageURI(imageUri)
        }
    }

    private fun saveRecipeToFirebase() {
        val recipeName = edtRecipeName.text.toString()
        val preparation = edtPreparation.text.toString()

        if (recipeName.isEmpty() || preparation.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val storageRef = FirebaseStorage.getInstance().reference.child("recipe_images/${UUID.randomUUID()}")


        // Subir la imagen a Firebase Storage
        imageUrl?.let {
            storageRef.putFile(it)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        // Guardar datos en Firestore
                        val recipeData = hashMapOf(
                            "name" to recipeName,
                            "preparation" to preparation,
                            "ingredients" to ingredientList,
                            "imageUrl" to uri.toString()
                        )

                        firestore.collection("recipes")
                            .add(recipeData)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Receta guardada exitosamente", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error al guardar la receta: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
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
}*/