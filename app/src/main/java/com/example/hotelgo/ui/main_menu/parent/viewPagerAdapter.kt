package com.example.hotelgo.ui.main_menu.parent

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.hotelgo.ui.main_menu.booking.BookingOwnerFragment
import com.example.hotelgo.ui.main_menu.home.HomeFragment
import com.example.hotelgo.ui.main_menu.profile.ProfileFragment

class viewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragment = listOf(HomeFragment(), BookingOwnerFragment(), ProfileFragment())


    override fun getItem(position: Int) = fragment[position]


    override fun getCount():Int = fragment.count()

}