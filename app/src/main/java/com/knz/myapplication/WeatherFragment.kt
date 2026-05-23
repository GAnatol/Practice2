package com.knz.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    val api_key = "55e00b845d5f04aa9834cc0d9a0db195"

    private lateinit var btVar1: Button
    private lateinit var textView: TextView
    private lateinit var cityInput: EditText
    private lateinit var searchBtn: ImageButton
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textView = view.findViewById(R.id.textView)
        btVar1 = view.findViewById(R.id.btVar1)
        cityInput = view.findViewById(R.id.city_input)
        searchBtn = view.findViewById(R.id.search_btn)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        btVar1.setOnClickListener {
            checkForPermission()
        }

        searchBtn.setOnClickListener {
            val cityName = cityInput.text.toString().trim()
            if (cityName.isNotEmpty()) {
                val encodedCity = java.net.URLEncoder.encode(cityName, "UTF-8")
                val weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=${encodedCity}&units=metric&appid=${api_key}&lang=ru"
                getTemp(weatherUrl)
                lifecycleScope.launch {
                    delay(1000)
                    it.hideKeyboard()
                }
            } else {
                Toast.makeText(requireContext(), "Введите название города", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun View.hideKeyboard() {
        val input = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun checkForPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            obtainLocation()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                obtainLocation()
            } else {
                Toast.makeText(requireContext(), "Разрешение отклонено", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun obtainLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=${location.latitude}&lon=${location.longitude}&units=metric&appid=${api_key}&lang=ru"
                    getTemp(weatherUrl)
                } else {
                    Toast.makeText(requireContext(), "Не удалось получить местоположение", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Ошибка получения геолокации", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getTemp(url: String) {
        val queue = Volley.newRequestQueue(requireContext())
        val stringReq = @SuppressLint("SetTextI18n")
        object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                val obj = JSONObject(response)
                val main: JSONObject = obj.getJSONObject("main")
                val temperature = main.getString("temp")
                val city = obj.getString("name")

                textView.text = "${temperature}°C в г. ${city}"
            },
            Response.ErrorListener {
                textView.text = "Ошибка"
            }
        ) {
            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                val utf8String = String(response.data, Charsets.UTF_8)
                return Response.success(
                    utf8String,
                    HttpHeaderParser.parseCacheHeaders(response)
                )
            }
        }

        queue.add(stringReq)
    }
}
