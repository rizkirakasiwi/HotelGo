package com.example.hotelgo.firebase

import com.example.hotelgo.data.DataProfile

interface FirebaseProfileCallback {
    fun onLoadData(data : DataProfile)
}