package com.example.myapplication

<<<<<<< HEAD
import android.content.Intent
import android.os.Bundle
import android.widget.Button
=======
import android.os.Bundle
>>>>>>> 0ce173def16e5d721a5ba72cba8578051ed0a5b1
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class inicio3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
<<<<<<< HEAD
        val botonContinuar3: Button = findViewById(R.id.botonContinuar3)
        botonContinuar3.setOnClickListener {
            Intent(this, Principal::class.java)
                .also { welcomeIntent ->
                    //Launch
                    startActivity(welcomeIntent)
                }
        }
=======
>>>>>>> 0ce173def16e5d721a5ba72cba8578051ed0a5b1
    }
}