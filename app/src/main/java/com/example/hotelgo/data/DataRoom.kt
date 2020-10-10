package com.example.hotelgo.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataRoom(
    val id :String?,
    val deskripsi : String?,
    val nama:String?,
    val harga:String?,
    val image:String?,
    val jumlahKamar:String?,
    val kamarKosong:String?,
    val kapasitas:String?
) : Parcelable {
    companion object{
        val ID = "id"
        val DESKRIPSI = "deskripsi"
        val HARGA = "harga"
        val IMAGE = "image"
        val NAMA = "nama"
        val JUMLAH_KAMAR = "jumlahKamar"
        val KAMAR_KOSONG = "kamarKosong"
        val KAPASITAS = "kapasitas"
    }
}