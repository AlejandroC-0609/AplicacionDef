package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Principal : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val iniciarsesion: Button = findViewById(R.id.iniciarsesion)
        iniciarsesion.setOnClickListener {
            Intent(this, IniciarSesion::class.java)
                .also { welcomeIntent ->
                    //Launch
                    startActivity(welcomeIntent)
                }
            val textViewCrearCuenta = findViewById<TextView>(R.id.textView24)
            textViewCrearCuenta.setOnClickListener {
                // Navegar a la página de registro
                val intent = Intent(this, Registro::class.java)
                startActivity(intent)
            }
        }
    }
}