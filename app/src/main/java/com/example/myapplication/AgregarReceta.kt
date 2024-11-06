package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginTop
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AgregarReceta : AppCompatActivity() {

    private lateinit var imgReceta: ImageView
    private lateinit var nombreReceta: EditText
    private lateinit var preparacion: EditText
    private lateinit var tiempo: EditText
    private lateinit var dificultad: EditText
    private lateinit var porciones: EditText
    private lateinit var calorias: EditText
    private val listaIngredientes = mutableListOf<String>()
    private lateinit var contenedorIngredientes: LinearLayout
    private lateinit var seleccionarIamgen: ActivityResultLauncher<Intent>
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
        contenedorIngredientes = findViewById(R.id.contenedorIngredientes)
        tiempo = findViewById(R.id.tiempoCampoAgregar)
        dificultad = findViewById(R.id.dificultadCampoAgregar)
        porciones = findViewById(R.id.porcionesCampoAgregar)
        calorias = findViewById(R.id.caloriasCampoAgregar)


        findViewById<Button>(R.id.agregarImagen).setOnClickListener {
            seleccionarImagen()
        }

        findViewById<Button>(R.id.agregarIngrediente).setOnClickListener {
            val campoIngrediente = EditText(this).apply {
                layoutParams = LinearLayout.LayoutParams(300, 46).apply {
                    topMargin = 20
                }
                background = ContextCompat.getDrawable(this@AgregarReceta, R.drawable.roundededittext)
                hint = getString(R.string.editar)
                inputType = InputType.TYPE_CLASS_TEXT
                setPadding(12, 0, 0, 0)
                setTextColor(Color.BLACK)
                textSize = 18f
            }
            contenedorIngredientes.addView(campoIngrediente)
        }

        seleccionarIamgen = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data
                if (uri != null) {
                    imgReceta.setImageURI(uri)
                    imageUri = uri
                }
            }
        }
        findViewById<Button>(R.id.button6).setOnClickListener {
            guardarRecetaFirebase()
        }

        findViewById<ImageButton>(R.id.atras).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun seleccionarImagen() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        seleccionarIamgen.launch(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {
            imageUri = data?.data
            imgReceta.setImageURI(imageUri)
        }
    }

    private fun guardarRecetaFirebase() {
        val nombreReceta = nombreReceta.text.toString()
        val dificultad = dificultad.text.toString()
        val porciones = porciones.text.toString()
        val tiempo = tiempo.text.toString()
        val calorias = calorias.text.toString()
        val preparacion = preparacion.text.toString()

        if (nombreReceta.isEmpty() || preparacion.isEmpty() || dificultad.isEmpty() || porciones.isEmpty() || tiempo.isEmpty() || calorias.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        listaIngredientes.clear()
        for (i in 0 until contenedorIngredientes.childCount) {
            val campoIngrediente = contenedorIngredientes.getChildAt(i) as EditText
            val ingrediente = campoIngrediente.text.toString()
            if (ingrediente.isNotEmpty()) {
                listaIngredientes.add(ingrediente)
            }
        }

        val firestore = FirebaseFirestore.getInstance()
        val referenciaMeta = firestore.collection("meta").document("configuracion")
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        referenciaMeta.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val ultimoIdReceta = document.getLong("ultimoIdReceta")?.toInt() ?: 99
                val nuevoIdReceta = ultimoIdReceta + 1
                val storageRef =
                    FirebaseStorage.getInstance().reference.child("imagenesRecetas/${UUID.randomUUID()}")
                imageUri?.let {
                    storageRef.putFile(it)
                        .addOnSuccessListener { taskSnapshot ->
                            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                                val datosReceta = hashMapOf(
                                    "nombreReceta" to nombreReceta,
                                    "preparacion" to preparacion,
                                    "dificultad" to dificultad,
                                    "porciones" to porciones,
                                    "tiempoPreparacion" to tiempo,
                                    "ingredientes" to listaIngredientes,
                                    "imageUrl" to uri.toString(),
                                    "recetaId" to nuevoIdReceta,
                                )

                                firestore.collection("usuarios").document(userId).collection("MisRecetas")
                                    .add(datosReceta)
                                    .addOnSuccessListener {
                                        referenciaMeta.update("ultimoIdReceta", nuevoIdReceta)
                                            .addOnSuccessListener {
                                                Toast.makeText(
                                                    this,
                                                    "Receta guardada exitosamente",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                finish()
                                            }
                                            .addOnFailureListener { e ->
                                                Toast.makeText(
                                                    this,
                                                    "Error al actualizar el último ID: ${e.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(
                                            this,
                                            "Error al guardar la receta: ${e.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "Error al subir la imagen: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }else {
                Toast.makeText(this, "Error al obtener el último ID", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Error al acceder a Firebase: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }
}
