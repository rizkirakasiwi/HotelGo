package com.example.hotelgo.firebase

import com.example.hotelgo.data.DataHotel
import com.example.hotelgo.data.DataRoom

interface FirebaseHotelCallback{
    fun onCallbackDataHotel(data:ArrayList<DataHotel?>)
    fun onCallBackRoom(data:ArrayList<DataRoom?>)
}