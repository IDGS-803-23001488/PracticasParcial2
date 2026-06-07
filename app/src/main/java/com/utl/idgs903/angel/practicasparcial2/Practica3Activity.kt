package com.utl.idgs903.angel.practicasparcial2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.utl.idgs903.angel.practicasparcial2.utils.setStatusBarColor
import java.util.Calendar

class Practica3Activity : AppCompatActivity() {

    private lateinit var edtNombre: EditText
    private lateinit var edtApellidoPaterno: EditText
    private lateinit var edtApellidoMaterno: EditText
    private lateinit var edtDia: EditText
    private lateinit var edtMes: EditText
    private lateinit var edtAnio: EditText
    private lateinit var rgSexo: RadioGroup
    private lateinit var btnImprimir: Button

    private lateinit var txtResultadoNombre: TextView
    private lateinit var txtResultadoEdad: TextView
    private lateinit var txtResultadoSigno: TextView
    private lateinit var imgSigno: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practica3)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setStatusBarColor(this, this, findViewById<ConstraintLayout>(R.id.main), window)

        edtNombre = findViewById(R.id.edtNombre)
        edtApellidoPaterno = findViewById(R.id.edtApellidoPaterno)
        edtApellidoMaterno = findViewById(R.id.edtApellidoMaterno)
        edtDia = findViewById(R.id.edtDia)
        edtMes = findViewById(R.id.edtMes)
        edtAnio = findViewById(R.id.edtAnio)
        rgSexo = findViewById(R.id.rgSexo)
        btnImprimir = findViewById(R.id.btnImprimir)
        imgSigno = findViewById(R.id.imgP3)

        btnImprimir.setOnClickListener { procesarInformacion() }
    }

    private fun procesarInformacion() {
        val nombre = edtNombre.text.toString()
        val apePat = edtApellidoPaterno.text.toString()
        val apeMat = edtApellidoMaterno.text.toString()
        val strAnio = edtAnio.text.toString()

        if (nombre.isEmpty() || apePat.isEmpty() || apeMat.isEmpty() || strAnio.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val anioNac = strAnio.toInt()
        val anioActual = Calendar.getInstance().get(Calendar.YEAR)
        val edad = anioActual - anioNac

        val (signo, idImagen) = obtenerSignoChino(anioNac)
        imgSigno.setImageResource(idImagen)

        val saludo = "Hola $nombre $apePat $apeMat\nTienes $edad años\nTu signo zodiacal es $signo"
        Toast.makeText(this, saludo, Toast.LENGTH_LONG).show()
    }

    private fun obtenerSignoChino(anio: Int): Pair<String, Int> {
        return when (anio % 12) {
            0 -> Pair("Mono", R.drawable.the_monkey)
            1 -> Pair("Gallo", R.drawable.rooster)
            2 -> Pair("Perro", R.drawable.dog)
            3 -> Pair("Cerdo", R.drawable.pig)
            4 -> Pair("Rata", R.drawable.rat)
            5 -> Pair("Buey", R.drawable.ox)
            6 -> Pair("Tigre", R.drawable.tiger)
            7 -> Pair("Conejo", R.drawable.rabbit)
            8 -> Pair("Dragón", R.drawable.dragon)
            9 -> Pair("Serpiente", R.drawable.snake)
            10 -> Pair("Caballo", R.drawable.horse)
            11 -> Pair("Cabra", R.drawable.goat)
            else -> Pair("Desconocido", R.drawable.number)
        }
    }
}