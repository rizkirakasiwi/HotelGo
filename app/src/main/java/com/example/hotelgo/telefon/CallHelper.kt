package com.example.hotelgo.telefon

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity


//class untuk menelfon hotel
class CallHelper(private val context: Context) {
    fun getCall(number:String?){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
            context.startActivity(intent)
        }
    }
}