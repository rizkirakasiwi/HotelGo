package com.example.hotelgo.firebase

import com.example.hotelgo.data.DataHotel

interface HotelCallback {
    fun onLoadHotelData(data:DataHotel)
}