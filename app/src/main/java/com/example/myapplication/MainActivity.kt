package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

var sesion_iniciada = false;

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val sesionIniciada = sharedPreferences.getBoolean("sesionIniciada", false)

        if (!sesionIniciada) {
            val intent = Intent(this, Inicio1::class.java)
            startActivity(intent)
            finish()
        } else {
            setContentView(R.layout.activity_recetas)
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
}