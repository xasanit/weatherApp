package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("current.json") // Указываем конечную точку
    fun getCurrentWeather(
        @Query("key") apiKey: String, // Параметр API-ключа
        @Query("q") location: String // Параметр для поиска города
    ): Call<WeatherResponse> // Возвращаемый объект
}