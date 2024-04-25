package com.example.myntcodechallenge.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myntcodechallenge.CurrentWeatherFragment
import com.example.myntcodechallenge.PrevWeathersFragment

/**
 * Created by Ivan Esguerra on 4/24/2024.
 **/
class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle)
{
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 1)
            PrevWeathersFragment()
        else
            CurrentWeatherFragment()
    }

}