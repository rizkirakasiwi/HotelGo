package com.example.hotelgo.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotelgo.data.DataHotel

object PassDataHotel {
    //menyimpan data DataHotel realtime
    private val _hotelData = MutableLiveData<DataHotel>()
    val hotelData : LiveData<DataHotel> get() = _hotelData

    //menginisialisasi _hoteldata
    fun setHotelData(data : DataHotel?){
        _hotelData.value = data
    }
}