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
        val location: Location,
        val current: Current
    )

    data class Location(
        val name: String,
        val country: String
    )

    data class Current(
        val temp_c: Double,
        val temp_f: Double,
        val humidity: Int,
        val condition: Condition,
        val wind_mph: Double,
        val uv: Double,
    )
    
    data class Condition(
        val text: String
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
        @GET("current.json")
        suspend fun getCurrentWeather(
            @Query("key") apiKey: String,
            @Query("q") query: String,
        ): Response<WeatherResponse>
    }

    private suspend fun fetchWeatherData(cityName: String): WeatherResponse {
        return withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(OpenWeatherMapService::class.java)
            val apiKey= "26cf1eec57b1464fb92211836242404"
            val response = service.getCurrentWeather(apiKey, cityName)

            if (response.isSuccessful) {
                response.body() ?: throw IllegalStateException("Weather Response Body is Null")
            }
            else {
                throw IllegalStateException("Failed to fetch weather data: ${response.errorBody()}")
            }
        }
    }
}