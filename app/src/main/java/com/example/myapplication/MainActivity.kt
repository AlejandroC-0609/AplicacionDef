package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

val sesion_iniciada = false;

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!sesion_iniciada){
            setContentView(R.layout.activity_inicio1)
            Intent(this, Inicio1::class.java)
                .also { welcomeIntent ->
                    //Launch
                    startActivity(welcomeIntent)
                }
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