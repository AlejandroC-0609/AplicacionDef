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

class IniciarSesion : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciar_sesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val iniciarsesion1: Button = findViewById(R.id.iniciarsesion1)
        iniciarsesion1.setOnClickListener {
            Intent(this, Recetas::class.java)
                .also { welcomeIntent ->
                    //Launch
                    startActivity(welcomeIntent)
                }
            val textViewCrearCuenta = findViewById<TextView>(R.id.textView16)
            textViewCrearCuenta.setOnClickListener {
                val intent = Intent(this, Registro::class.java)
                startActivity(intent)
            }
            val textViewOlvide = findViewById<TextView>(R.id.textView17)
            textViewOlvide.setOnClickListener {
                val intent = Intent(this, Reestablecer::class.java)
                startActivity(intent)
            }
        }
    }
}