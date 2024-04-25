package com.example.project_4
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project_4.databinding.FragmentCityBinding


class CityFragment : Fragment() {

    interface CitySelectionListener {
        fun onCitySelected(city: String)
    }

    private lateinit var binding: FragmentCityBinding
    private var citySelectionListener: CitySelectionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun getEnteredCity(): String {
        return binding.edCity.text.toString()
    }
    fun setCitySelectionListener(listener: CitySelectionListener) {
        citySelectionListener = listener
    }
}