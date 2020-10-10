package com.example.hotelgo.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataHotel(
        val uid:String?,
        val email:String?,
        val nama:String?,
        val rating:String?,
        val alamat:String?,
        val mapLat:String?,
        val mapLong:String?,
        val deskripsi:String?,
        val fasilitasUmum:String?,
        val image:String?,
        val hargaMulai:String?,
        val noTelfon:String?,
        val kodePos:String?
    ):Parcelable{
        companion object{
                val UID = "uid"
                val EMAIL = "email"
                val NAMA = "nama"
                val RATING = "rating"
                val ALAMAT = "alamat"
                val FASILITAS_UMUM = "fasilitasUmum"
                val MAP_LAT = "mapLat"
                val MAP_LONG = "mapLong"
                val DESKRIPSI = "deskripsi"
                val IMAGE = "image"
                val HARGA_MULAI = "hargaMulai"
                val NO_TELP = "noTelfon"
                val KODE_POS = "kodePos"
        }
}