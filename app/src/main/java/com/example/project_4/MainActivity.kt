package com.example.project_4

import androidx.appcompat.app.AppCompatActivity
import com.example.project_4.databinding.ActivityMainBinding
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private var GEO_API_URL = "https://wft-geo-db.p.rapidapi.com/v1/geo";
    private var WEATHER_API_URL = "https://api.openweathermap.org/data/2.5";
    private val API_KEY = "";
    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherFragment: WeatherFragment
    private lateinit var cityFragment: CityFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetWeather.setOnClickListener {

            //val city = cityFragment.getCity()   Error: City Fragment needs to be instantiated?
        }
    }
}