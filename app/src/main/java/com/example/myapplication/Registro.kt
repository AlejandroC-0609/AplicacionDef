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

class Registro : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val registrarse: Button = findViewById(R.id.registrarse)
        registrarse.setOnClickListener {
            Intent(this, Recetas::class.java)
                .also { welcomeIntent ->
                    //Launch
                    startActivity(welcomeIntent)
                }
            val textViewIniciar = findViewById<TextView>(R.id.textView11)
            textViewIniciar.setOnClickListener {
                val intent = Intent(this, IniciarSesion::class.java)
                startActivity(intent)
            }
        }
    }
}