package com.example.hotelgo.controller

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object FragmentNow{
    //menyimpan data fragment temporary secara realtime
    private val _fragment = MutableLiveData<Fragment?>()
    val fragment : LiveData<Fragment?> get() = _fragment

}