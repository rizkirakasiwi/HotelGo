package com.example.hotelgo.ui.main_menu.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelgo.data.DataHotel

class HomeViewModel : ViewModel() {
    private val _hotelData = MutableLiveData<ArrayList<DataHotel?>>()
    val hotelData : LiveData<ArrayList<DataHotel?>> get() = _hotelData

    private val TAG = "HomeViewModel"

    fun setHotelData(data:ArrayList<DataHotel?>){
        _hotelData.value?.clear()
        _hotelData.value = data
    }

}
