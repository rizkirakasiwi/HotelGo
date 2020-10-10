package com.example.hotelgo.firebase

import com.example.hotelgo.data.DataBooking

interface BookingCallback {
    fun onLoadBookingData(data : ArrayList<DataBooking>?)
}