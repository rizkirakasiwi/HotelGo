package com.example.hotelgo.ui.detail.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hotelgo.controller.PassDataHotel
import com.example.hotelgo.data.DataHotel

class DetailLayoutViewModel : ViewModel() {
    val dataHotel : LiveData<DataHotel> get() = PassDataHotel.hotelData
}
