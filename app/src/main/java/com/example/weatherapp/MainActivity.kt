package com.example.weatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val apiService = WeatherApiClient.retrofit.create(WeatherApiService::class.java)

        binding.confirmButton.setOnClickListener {
            val location = binding.cityName.text.toString() // Получаем город из EditText
            if (location.isNotEmpty()) {
                fetchWeather(apiService, location)
            } else {
                Toast.makeText(this, "Введите название города", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchWeather(apiService: WeatherApiService, location: String) {
        // Отправляем запрос в API
        apiService.getCurrentWeather("f241ac698e524b2ca87205847251001", location).enqueue(object :
            Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weather = response.body()
                    weather?.let {
                        // Обновляем UI с данными о погоде
                        binding.cityNameView.text = it.location.name
                        binding.temperatureView.text = "${it.current.temp_c}°C"
                        binding.weatherCondition.text = it.current.condition.text
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Ошибка: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
