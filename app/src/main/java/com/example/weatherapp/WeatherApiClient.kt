package com.example.weatherapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherApiClient {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Указываем базовый URL
            .addConverterFactory(GsonConverterFactory.create()) // Конвертер JSON
            .build()
    }
}
