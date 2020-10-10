package com.example.hotelgo.firebase

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthHelper {
    private val auth = FirebaseAuth.getInstance()
    val uid = auth.currentUser?.uid
    fun isLogin() = auth.currentUser

    fun createUser(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    fun loginAuth(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)

    fun getEmail() = auth.currentUser?.email

}