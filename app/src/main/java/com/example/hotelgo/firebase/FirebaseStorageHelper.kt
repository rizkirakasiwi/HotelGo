package com.example.hotelgo.firebase

import android.net.Uri
import android.util.Log
import com.example.hotelgo.data.DataBooking
import com.example.hotelgo.data.DataHotel
import com.example.hotelgo.data.DataProfile
import com.example.hotelgo.data.DataRoom
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.util.*

object FirebaseStorageHelper {
    private val storage = FirebaseStorage.getInstance()
    private val userStorageRef = storage.getReference("user")

    fun insertImageUserToStorage(stream: Uri, firebaseStorageInfo: FirebaseStorageInfo) =
        GlobalScope.launch(Dispatchers.IO) {

            val ref = userStorageRef.child("${UUID.randomUUID()}.jpeg")
            val uploadTask = ref.putFile(stream)
            uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    ref.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseStorageInfo.onCompletedUploadListener(true, task.result.toString())
                    } else {
                        firebaseStorageInfo.onCompletedUploadListener(false, null)
                    }
                }
        }
}