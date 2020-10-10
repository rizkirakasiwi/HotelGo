package com.example.hotelgo.ui.main_menu.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelgo.data.DataBooking
import com.example.hotelgo.data.DataHotel
import com.example.hotelgo.data.DataRoom

class BookingOwnerViewModel : ViewModel() {
    private val _hotelData = MutableLiveData<ArrayList<DataHotel?>>()
    val hotelData : LiveData<ArrayList<DataHotel?>> get() = _hotelData

    private val _bookingData = MutableLiveData<ArrayList<DataBooking>?>()
    val bookingData : LiveData<ArrayList<DataBooking>?> get() = _bookingData

    private val _room = MutableLiveData<DataRoom?>()
    val room : LiveData<DataRoom?> get() = _room

    fun setBookingData(data : ArrayList<DataBooking>?){
        _bookingData.value = data
    }

    fun setHotelData(data:ArrayList<DataHotel?>){
        _hotelData.value = data
    }


    fun setRoomData(data:DataRoom?){
        _room.value = data
    }
}
