package com.example.project_4

import androidx.appcompat.app.AppCompatActivity
import com.example.project_4.databinding.ActivityMainBinding
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CityFragment.CitySelectionListener {
    private var WEATHER_API_URL = "http://api.weatherapi.com/v1/"
    private val API_KEY = "26cf1eec57b1464fb92211836242404"
    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherFragment: WeatherFragment
    private lateinit var cityFragment: CityFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weatherFragment = WeatherFragment()
        cityFragment = CityFragment()

        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.add(R.id.WeatherFragmentContainer, weatherFragment)
        transaction.add(R.id.CityFragmentContainer, cityFragment)
        transaction.commit()

        binding.btnGetWeather.setOnClickListener {
            val city = cityFragment.getEneteredCity()
            onCitySelected(city)
        }
    }
    override fun onCitySelected(city: String) {
        lifecycleScope.launch {
            weatherFragment.fetchWeatherData(city)
        }

    }
}