package com.example.hotelgo.data

data class DataBooking(
    val uid: String?,
    val jumlahKamaar: String?,
    val total: String?,
    val idHotel: String?,
    val status:String?,
    val namaRoom: String?
){
    companion object{
        val UID ="uid"
        val JUMLAH_KAMAR = "jumlahKamaar"
        val TOTAL = "total"
        val ID_HOTEL = "idHotel"
        val NAMA_ROOM = "namaRoom"
        val STATUS = "status"
    }
}