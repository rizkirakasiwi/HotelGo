package com.example.hotelgo.data

data class DataProfile(
    val uid : String?,
    val nama :String?,
    val jenisKelamin:String?,
    val tanggalLahir:String?,
    val alamat:String?,
    val email:String?,
    val telfon:String?,
    val image:String?
){
    companion object{
        val UID = "uid"
        val NAMA = "nama"
        val JK = "jenisKelamin"
        val TTL = "tanggalLahir"
        val ALAMAT = "alamat"
        val EMAIL = "email"
        val TELFON = "telfon"
        val IMAGE = "image"
    }
}