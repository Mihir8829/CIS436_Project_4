package com.example.project_4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.project_4.databinding.FragmentWeatherBinding
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalStateException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }

    data class WeatherResponse(
        val main: Main,
        val weather: List<Weather>
    )

    data class Main(
        val temp: Double,
        val humidity: Int
    )

    data class Weather(
        val description: String
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       lifecycleScope.launch {
           val weatherResponse = fetchWeatherData("Detroit")
       }
    }

    interface OpenWeatherMapService {
        @GET("data/2.5/weather")
        suspend fun getCurrentWeather(
            @Query("q") cityName: String,
            @Query("appid") apiKey: String,
        ): Response<WeatherResponse>
    }

    private suspend fun fetchWeatherData(cityName: String): WeatherResponse {
        return withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(OpenWeatherMapService::class.java)

            val apiKey= ""
            val response = service.getCurrentWeather(cityName, apiKey)

            if (response.isSuccessful) {
                response.body() ?: throw IllegalStateException("Weather Response Body is Null")
            }
            else {
                throw IllegalStateException("Failed to fetch weather data: ${response.errorBody()}")
            }
        }
    }
}