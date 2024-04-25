package com.example.myntcodechallenge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myntcodechallenge.adapter.WeatherListAdapter
import com.example.myntcodechallenge.databinding.FragmentCurrentWeatherBinding
import com.example.myntcodechallenge.databinding.FragmentPrevWeathersBinding
import com.example.myntcodechallenge.model.Weather
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PrevWeathersFragment : Fragment() {
    private lateinit var binding: FragmentPrevWeathersBinding
    private lateinit var dataList: ArrayList<Weather>
    private var mAdapter: WeatherListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWeatherList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPrevWeathersBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun getWeatherList() {
        dataList = ArrayList()
        val rootRef = FirebaseDatabase.getInstance().reference
        val weatherRef = rootRef.child("weathers")
        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                val list: MutableList<Weather> = ArrayList()
                for (userSnapshot in dataSnapshot.children) {
                    try {
                        val name = userSnapshot.child("name").getValue(String::class.java)
                        val main = userSnapshot.child("main").getValue(Weather.Main::class.java)
                        val sys = userSnapshot.child("sys").getValue(Weather.Sys::class.java)
                        // create Weather instance
                        val weather = Weather(main!!, name!!, sys!!)
                        list.add(weather)
                        //Todo: "Handle getting of array of weatherx value"
                    } catch (e: Exception) {
                        Log.w("Error", "Parse failed", e)
                    }
                }
                mAdapter = WeatherListAdapter(requireContext(), list)
                val linearLayoutManager: RecyclerView.LayoutManager =
                    LinearLayoutManager(requireContext())
                binding.recyclerView.layoutManager = linearLayoutManager
                binding.recyclerView.adapter = mAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Fetch Error", databaseError.message)
            }
        }
        weatherRef.addListenerForSingleValueEvent(valueEventListener)
    }
}
