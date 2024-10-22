package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditarRecetas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_recetas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val listView = findViewById<ListView>(R.id.listView)
        val items = listOf(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            "Aliquam at dignissim ante.",
            "In pretium laoreet accumsan.",
            "In ipsum arcu, pulvinar ac placerat nec, malesuada efficitur tortor.",
            "Fusce dapibus erat mauris.",
            "Praesent malesuada urna quis nisi malesuada rutrum.",
            "Sed vulputate bibendum varius."
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter
    }
}