package com.utl.idgs903.angel.practicasparcial2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.utl.idgs903.angel.practicasparcial2.utils.setStatusBarColor
import kotlin.math.sqrt

class Practica2Activity : AppCompatActivity() {
    private lateinit var edtLadoA : EditText
    private lateinit var edtLadoB : EditText
    private lateinit var edtLadoC : EditText
    private lateinit var txtResultadoValidacion : TextView
    private lateinit var btnValidarTriangulo : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practica2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        try {
            setStatusBarColor(this, this, findViewById<ConstraintLayout>(R.id.main), window)
        } catch (e: Exception) { e.printStackTrace() }

        edtLadoA = findViewById(R.id.edtLadoA)
        edtLadoB = findViewById(R.id.edtLadoB)
        edtLadoC = findViewById(R.id.edtLadoC)
        txtResultadoValidacion = findViewById(R.id.txtResultadoValidacion)
        btnValidarTriangulo = findViewById(R.id.btnValidarTriangulo)

        btnValidarTriangulo.setOnClickListener { validarYCalcularArea() }
    }

    private fun validarYCalcularArea() {
        val strA = edtLadoA.text.toString()
        val strB = edtLadoB.text.toString()
        val strC = edtLadoC.text.toString()

        if (strA.isEmpty() || strB.isEmpty() || strC.isEmpty()) {
            Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val ladoA = strA.toDouble()
        val ladoB = strB.toDouble()
        val ladoC = strC.toDouble()

        val esTriangulo = (ladoA + ladoB > ladoC) && (ladoA + ladoC > ladoB) && (ladoB + ladoC > ladoA)

        if (!esTriangulo) {
            Toast.makeText(this, "No es un triangulo valido", Toast.LENGTH_LONG).show()
            txtResultadoValidacion.text = "Las medidas no forman un triángulo."
        } else {
            val s = (ladoA + ladoB + ladoC) / 2.0
            val area = sqrt(s * (s - ladoA) * (s - ladoB) * (s - ladoC))

            txtResultadoValidacion.text = "TRIANGULO: Área: %.2f".format(area)
        }
    }
}