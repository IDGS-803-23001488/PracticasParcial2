package com.utl.idgs903.angel.practicasparcial2

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.utl.idgs903.angel.practicasparcial2.utils.setStatusBarColor
import kotlin.math.pow

class CalculadoraResistenciasActivity : AppCompatActivity() {

    private lateinit var spinnerBanda1: Spinner
    private lateinit var spinnerBanda2: Spinner
    private lateinit var spinnerBanda3: Spinner
    
    private lateinit var etValorBanda1: EditText
    private lateinit var etValorBanda2: EditText
    private lateinit var etValorBanda3: EditText
    
    private lateinit var rgTolerancia: RadioGroup
    private lateinit var rbOro: RadioButton
    private lateinit var rbPlata: RadioButton
    
    private lateinit var tvValorOhm: TextView
    private lateinit var tvValorMaximo: TextView
    private lateinit var tvValorMinimo: TextView
    private lateinit var btnCalcular: Button

    data class ColorResistencia(val nombre: String, val colorHex: Int)

    inner class ColorAdapter(context: android.content.Context, private val colores: List<ColorResistencia>) :
        ArrayAdapter<ColorResistencia>(context, 0, colores) {

        override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
            return createView(position, convertView, parent)
        }

        override fun getDropDownView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
            return createView(position, convertView, parent)
        }

        private fun createView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
            val view = convertView ?: layoutInflater.inflate(R.layout.item_spinner_color, parent, false)
            val color = getItem(position)
            val circle = view.findViewById<android.view.View>(R.id.colorCircle)
            val name = view.findViewById<TextView>(R.id.colorName)

            color?.let {
                name.text = it.nombre
                val drawable = androidx.core.content.ContextCompat.getDrawable(context, R.drawable.circle_background)?.mutate() as? android.graphics.drawable.GradientDrawable
                drawable?.setColor(it.colorHex)
                circle.background = drawable
            }
            return view
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculadora_resistencias)
        title = "Calculadora de Resistencias"
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setStatusBarColor(this, this, findViewById<LinearLayout>(R.id.main), window)
        
        spinnerBanda1 = findViewById(R.id.spinnerBanda1)
        spinnerBanda2 = findViewById(R.id.spinnerBanda2)
        spinnerBanda3 = findViewById(R.id.spinnerBanda3)
        
        etValorBanda1 = findViewById(R.id.etValorBanda1)
        etValorBanda2 = findViewById(R.id.etValorBanda2)
        etValorBanda3 = findViewById(R.id.etValorBanda3)
        
        rgTolerancia = findViewById(R.id.rgTolerancia)
        rbOro = findViewById(R.id.rbOro)
        rbPlata = findViewById(R.id.rbPlata)
        
        tvValorOhm = findViewById(R.id.tvValorOhm)
        tvValorMaximo = findViewById(R.id.tvValorMaximo)
        tvValorMinimo = findViewById(R.id.tvValorMinimo)
        btnCalcular = findViewById(R.id.btnCalcular)
        
        val listaColores = listOf(
            ColorResistencia("Negro", android.graphics.Color.parseColor("#000000")),
            ColorResistencia("Marrón", android.graphics.Color.parseColor("#8B4513")),
            ColorResistencia("Rojo", android.graphics.Color.parseColor("#FF0000")),
            ColorResistencia("Naranja", android.graphics.Color.parseColor("#FFA500")),
            ColorResistencia("Amarillo", android.graphics.Color.parseColor("#FFD700")),
            ColorResistencia("Verde", android.graphics.Color.parseColor("#008000")),
            ColorResistencia("Azul", android.graphics.Color.parseColor("#0000FF")),
            ColorResistencia("Violeta", android.graphics.Color.parseColor("#8A2BE2")),
            ColorResistencia("Gris", android.graphics.Color.parseColor("#808080")),
            ColorResistencia("Blanco", android.graphics.Color.parseColor("#FFFFFF"))
        )
        val adapter = ColorAdapter(this, listaColores)
        
        spinnerBanda1.adapter = adapter
        spinnerBanda2.adapter = adapter
        spinnerBanda3.adapter = adapter
        
        val itemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val etTarget = when (parent.id) {
                    R.id.spinnerBanda1 -> etValorBanda1
                    R.id.spinnerBanda2 -> etValorBanda2
                    R.id.spinnerBanda3 -> etValorBanda3
                    else -> return
                }
                val currentValue = etTarget.text.toString().toIntOrNull()
                if (currentValue != position) {
                    etTarget.setText(position.toString())
                    etTarget.setSelection(etTarget.text.length)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerBanda1.onItemSelectedListener = itemSelectedListener
        spinnerBanda2.onItemSelectedListener = itemSelectedListener
        spinnerBanda3.onItemSelectedListener = itemSelectedListener
        
        fun setupTextWatcher(et: EditText, spinner: Spinner) {
            et.addTextChangedListener(object : android.text.TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: android.text.Editable?) {
                    val value = s.toString().toIntOrNull()
                    if (value != null && value in 0..9) {
                        if (spinner.selectedItemPosition != value) {
                            spinner.setSelection(value)
                        }
                    }
                }
            })
        }

        setupTextWatcher(etValorBanda1, spinnerBanda1)
        setupTextWatcher(etValorBanda2, spinnerBanda2)
        setupTextWatcher(etValorBanda3, spinnerBanda3)
        
        rbOro.isChecked = true

        btnCalcular.setOnClickListener { calcularResistencia() }
    }

    private fun calcularResistencia() {
        val v1 = etValorBanda1.text.toString().toDoubleOrNull() ?: 0.0
        val v2 = etValorBanda2.text.toString().toDoubleOrNull() ?: 0.0
        val v3 = etValorBanda3.text.toString().toDoubleOrNull() ?: 0.0
        
        val base = (v1 * 10) + v2
        val multiplicador = 10.0.pow(v3)
        val valorOhm = base * multiplicador
        
        val toleranciaPct = if (rbPlata.isChecked) 0.10 else 0.05
        val variacion = valorOhm * toleranciaPct

        val valorMax = valorOhm + variacion
        val valorMin = valorOhm - variacion
        
        tvValorOhm.text = "Valor Ohm: ${formatearNumero(valorOhm)} Ω"
        tvValorMaximo.text = "Valor Máximo: ${formatearNumero(valorMax)} Ω"
        tvValorMinimo.text = "Valor Mínimo: ${formatearNumero(valorMin)} Ω"
    }

    private fun formatearNumero(num: Double): String {
        return if (num == num.toLong().toDouble()) {
            String.format("%,d", num.toLong())
        } else {
            String.format("%,.2f", num)
        }
    }
}
