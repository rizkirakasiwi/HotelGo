package com.example.hotelgo.firebase

import com.example.hotelgo.data.DataRoom

interface RoomDataCallback {
    fun onLoadRoomData(room:DataRoom?)
}