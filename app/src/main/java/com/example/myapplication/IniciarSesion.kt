package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class IniciarSesion : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_iniciar_sesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val iniciarsesion1: Button = findViewById(R.id.iniciarsesion1)
        iniciarsesion1.setOnClickListener {
            loginUser()
        }
        val textViewCrearCuenta = findViewById<TextView>(R.id.sinCuentaInicio)
        textViewCrearCuenta.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
        val textViewOlvide = findViewById<TextView>(R.id.recuperarCuentaInicio)
        textViewOlvide.setOnClickListener {
            val intent = Intent(this, Reestablecer::class.java)
            startActivity(intent)
        }
    }
        private fun loginUser() {
            val emailOusuario = findViewById<EditText>(R.id.usuarioOcorreo).text.toString().trim()
            val contraseña = findViewById<EditText>(R.id.contraseñaCampoInicio).text.toString().trim()

            if (emailOusuario.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
                return
            }

            auth.signInWithEmailAndPassword(emailOusuario, contraseña)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                        sesion_iniciada = true
                        startActivity(Intent(this, Principal::class.java))
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
}
