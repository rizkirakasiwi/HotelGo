package com.example.hotelgo.firebase

import com.example.hotelgo.firebase.FirebaseAuthHelper.uid
import com.google.firebase.database.FirebaseDatabase

object FirebaseBookingHelper {
    private val database = FirebaseDatabase.getInstance()
    private val bookingRef = database.getReference("user/$uid/booking")
}