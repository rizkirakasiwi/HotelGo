package com.example.hotelgo.ui.detail.pemesanan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelgo.data.DataRoom

class PemesananViewModel : ViewModel() {

    private val _roomData = MutableLiveData<ArrayList<DataRoom?>>()
    val dataRoomData : LiveData<ArrayList<DataRoom?>> get() = _roomData


    fun setRoomData(data:ArrayList<DataRoom?>){
        _roomData.value?.clear()
        _roomData.value = data
    }
}
