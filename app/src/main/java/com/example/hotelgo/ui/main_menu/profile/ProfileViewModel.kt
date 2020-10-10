package com.example.hotelgo.ui.main_menu.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelgo.data.DataProfile

class ProfileViewModel : ViewModel() {
    private val _path = MutableLiveData<Uri>()
    val path : LiveData<Uri> get() = _path

    private val _dataProfile = MutableLiveData<DataProfile>()
    val dataProfile : LiveData<DataProfile> get() = _dataProfile

    fun setDataProfile(myDataProfile: DataProfile){
        _dataProfile.value = myDataProfile
    }
    fun setPath(myPath:Uri){
        _path.value = myPath
    }
}
