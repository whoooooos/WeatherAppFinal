package com.example.myntcodechallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myntcodechallenge.databinding.FragmentCurrentWeatherBinding
import com.example.myntcodechallenge.databinding.WeatherItemLayoutBinding
import com.example.myntcodechallenge.util.RetrofitInstance
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CurrentWeatherFragment : Fragment() {
    private lateinit var binding: FragmentCurrentWeatherBinding
    private lateinit var weatherCardBinding: WeatherItemLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCurrentWeather("Manila")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        weatherCardBinding = binding.weatherCard
        return binding.root
    }

    private fun getCurrentWeather(city: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getCurrentWeather(
                    city,
                    "metric",
                    resources.getString(R.string.api_key)
                )
            } catch (e: IOException) {
                Toast.makeText(activity, "app error ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            } catch (e: HttpException) {
                Toast.makeText(activity, "http error ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val data = response.body()!!
                    val currentTime = Calendar.getInstance()

                    // set card visibility
                    weatherCardBinding.progressLoader.visibility = View.INVISIBLE
                    weatherCardBinding.cardMainLayout.visibility = View.VISIBLE
                    // handle setting of weather icon
                    setWeatherIcon(data.weather?.get(0)?.main, currentTime, weatherCardBinding.weatherIv)
                    weatherCardBinding.sunriseTimeTv.text = data.sys?.sunrise?.let {
                        dateFormatConverter(
                            it.toLong()
                        )
                    }
                    weatherCardBinding.sunsetTimeTv.text = data.sys?.sunset?.let {
                        dateFormatConverter(
                            it.toLong()
                        )
                    }
                    weatherCardBinding.locationTv.text = "${data.name} ${data.sys?.country}"
                    weatherCardBinding.tempTv.text = "${data.main?.temp?.toInt()}°C"
                    var firebaseDatabase = FirebaseDatabase.getInstance()
                    var databaseReference = firebaseDatabase.reference
                    databaseReference.child("weathers").push().setValue(data)
                }
            }
        }
    }

    private fun dateFormatConverter(date: Long): String {
        return SimpleDateFormat(
            "hh:mm a",
            Locale.ENGLISH
        ).format(Date(date * 1000))
    }

    fun setWeatherIcon(weather: String?="", currentTime: Calendar, view: ImageView) {
        // check if weather is Rain
        if(weather != "Rain") {
            //check if current time is past 6PM
            //set drawable image to moon if yes
            if (currentTime[Calendar.HOUR_OF_DAY] >= 18) {
                view.setImageResource(R.drawable.moon)
            } else {
                view.setImageResource(R.drawable.sun)
            }
        } else {
          view.setImageResource(R.drawable.rain)
        }
    }
}