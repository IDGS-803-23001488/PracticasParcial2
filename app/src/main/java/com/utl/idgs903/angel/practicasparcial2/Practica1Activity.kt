package com.utl.idgs903.angel.practicasparcial2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.utl.idgs903.angel.practicasparcial2.utils.setStatusBarColor

class Practica1Activity : AppCompatActivity() {

    private lateinit var edtX1 : EditText
    private lateinit var edtY1 : EditText
    private lateinit var edtX2 : EditText
    private lateinit var edtY2 : EditText
    private lateinit var txtResultadoDistancia : TextView
    private lateinit var btnCalcularDistancia : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practica1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setStatusBarColor(this, this, findViewById<ConstraintLayout>(R.id.main), window)

        edtX1 = findViewById<EditText>(R.id.edtX1)
        edtY1 = findViewById<EditText>(R.id.edtY1)
        edtX2 = findViewById<EditText>(R.id.edtX2)
        edtY2 = findViewById<EditText>(R.id.edtY2)
        txtResultadoDistancia = findViewById<TextView>(R.id.txtResultadoDistancia)
        btnCalcularDistancia = findViewById<Button>(R.id.btnCalcularDistancia)

        btnCalcularDistancia.setOnClickListener { calcularDistanciaDosPuntos() }
    }

    private fun calcularDistanciaDosPuntos(){
        var x1 = edtX1.text.toString().toDouble()
        var y1 = edtY1.text.toString().toDouble()
        var x2 = edtX2.text.toString().toDouble()
        var y2 = edtY2.text.toString().toDouble()
        val distancia = Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0))
        txtResultadoDistancia.text = "Distancia: ${String.format("%.2f", distancia)}"
    }

}