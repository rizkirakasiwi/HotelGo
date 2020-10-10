package com.example.hotelgo.controller

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.hotelgo.ui.detail.detail.DetailLayout
import com.example.hotelgo.ui.detail.pemesanan.Pemesanan

//membuat adapter untuk viewpager
class DetialViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    //list fragment
    private val fragment = listOf(DetailLayout(), Pemesanan())
    private val text = listOf("Detail", "Pemesanan")

    //menempelkan fragment pada viewpager
    override fun getItem(position: Int): Fragment = fragment[position]

    //jumlah list
    override fun getCount(): Int = fragment.count()

    //memberikan label pada fragment
    override fun getPageTitle(position: Int): CharSequence? {
        return text[position]
    }
}