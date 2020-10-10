package com.example.hotelgo.firebase

import android.net.Uri

interface FirebaseStorageInfo {
    fun onCompletedUploadListener(isSuccess:Boolean, image: String?)
}