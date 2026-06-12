package com.utl.idgs903.angel.practicasparcial2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.LinearLayout
import com.utl.idgs903.angel.practicasparcial2.utils.setStatusBarColor

class CinepolisActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCompradores: EditText
    private lateinit var etBoletas: EditText
    private lateinit var rbSi: RadioButton
    private lateinit var rbNo: RadioButton
    private lateinit var tvValorPagar: TextView
    private lateinit var btnProcesar: Button
    private lateinit var btnSalir: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cinepolis)
        title = "Cinepolis"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setStatusBarColor(this, this, findViewById<LinearLayout>(R.id.main), window)

        etNombre = findViewById(R.id.etNombre)
        etCompradores = findViewById(R.id.etCompradores)
        etBoletas = findViewById(R.id.etBoletas)
        rbSi = findViewById(R.id.rbSi)
        rbNo = findViewById(R.id.rbNo)
        tvValorPagar = findViewById(R.id.tvValorPagar)
        btnProcesar = findViewById(R.id.btnProcesar)
        btnSalir = findViewById(R.id.btnSalir)

        btnProcesar.setOnClickListener { procesarCompra() }
        btnSalir.setOnClickListener { finish() }
    }

    private fun procesarCompra() {
        val compradores = etCompradores.text.toString().toIntOrNull() ?: 0
        val boletas = etBoletas.text.toString().toIntOrNull() ?: 0

        if (compradores <= 0) {
            Toast.makeText(this, "Ingresa una cantidad válida de compradores", Toast.LENGTH_SHORT).show()
            return
        }
        if (boletas <= 0) {
            Toast.makeText(this, "Ingresa una cantidad válida de boletas", Toast.LENGTH_SHORT).show()
            return
        }

        val maxBoletas = compradores * 7

        if (boletas > maxBoletas) {
            Toast.makeText(this, "No puedes comprar más de 7 boletas por persona", Toast.LENGTH_LONG).show()
            tvValorPagar.text = "Valor a Pagar: $ 0.00"
            return
        }

        val precioBoleta = 12.0
        val totalBase = boletas * precioBoleta
        var descuentoBase = 0.0

        if (boletas > 5) {
            descuentoBase = 0.15
        } else if (boletas in 3..5) {
            descuentoBase = 0.10
        } else {
            descuentoBase = 0.0
        }

        var totalPagar = totalBase - (totalBase * descuentoBase)

        if (rbSi.isChecked) {
            totalPagar -= (totalPagar * 0.10)
        }

        tvValorPagar.text = String.format("Valor a Pagar: $ %,.2f", totalPagar)
    }
}
