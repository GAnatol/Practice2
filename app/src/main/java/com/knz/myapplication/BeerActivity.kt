package com.knz.myapplication

import android.os.Bundle
import android.widget.Spinner
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class BeerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_beer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBeer = findViewById<Button>(R.id.btnBeer)
        val btnRandomBeer = findViewById<Button>(R.id.btnRandomBeer)
        val tvBeer = findViewById<TextView>(R.id.tvBeer)
        val spinnerBeer = findViewById<Spinner>(R.id.spinBeer)

        btnBeer.setOnClickListener {
            val selectedType = spinnerBeer.selectedItem.toString()
            val beerList = getBeers(selectedType)
            tvBeer.text = beerList
        }

        btnRandomBeer.setOnClickListener {
            val beerArray = resources.getStringArray(R.array.type_beer)

            val randomIndex = Random.nextInt(beerArray.size)
            val randomBeerType = beerArray[randomIndex]

            spinnerBeer.setSelection(randomIndex)

            Toast.makeText(this, "Выпало: $randomBeerType!", Toast.LENGTH_SHORT).show()

            tvBeer.text = "Выпало: $randomBeerType\n\n${getBeers(randomBeerType)}"
        }
    }

    private fun getBeers(type: String): String {
        return when (type) {
            "темное" -> "• Guinness\n• Kozel Dark\n• Paulaner Salvator"
            "светлое" -> "• Pilsner Urquell\n• Heineken\n• Corona Extra"
            "ржаное" -> "• Franziskaner\n• Hoegaarden\n• Erdinger"
            "сладкое" -> "• Clausthaler\n• Baltika 0\n• Stella Artois 0.0"
            "горькое" -> "• IPA (BrewDog)\n• APA\n• Stout (Milk)"
            else -> "Сорта не найдены"
        }
    }
}
