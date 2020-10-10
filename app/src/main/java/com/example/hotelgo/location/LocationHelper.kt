package com.example.hotelgo.location

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity


class LocationHelper(private val context: Context) {
    fun getLocation(lat:String?, long:String?){
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://maps.google.com/maps?daddr=$lat,$long")
        )
        context.startActivity(intent)
    }
}