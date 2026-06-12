package com.utl.idgs903.angel.practicasparcial2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.utl.idgs903.angel.practicasparcial2.utils.setStatusBarColor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setStatusBarColor(this, this, findViewById<ConstraintLayout>(R.id.main), window)

        val btnOperaBas = findViewById<Button>(R.id.btn1)
        btnOperaBas.setOnClickListener { navigateTo(Practica1Activity::class.java) }

        val btnSaludo = findViewById<Button>(R.id.btn2)
        btnSaludo.setOnClickListener { navigateTo(Practica2Activity::class.java) }

        val btnEjemplo3 = findViewById<Button>(R.id.btn3)
        btnEjemplo3.setOnClickListener { navigateTo(Practica3Activity::class.java) }

        val btnPractica4 = findViewById<Button>(R.id.btn4)
        btnPractica4.setOnClickListener { navigateTo(CalculadoraResistenciasActivity::class.java) }

        val btnPractica5 = findViewById<Button>(R.id.btn5)
        btnPractica5.setOnClickListener { navigateTo(CinepolisActivity::class.java) }

    }

    private fun navigateTo(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }
}