package com.example.hotelgo.firebase

import android.util.Log
import com.example.hotelgo.data.DataBooking
import com.example.hotelgo.data.DataHotel
import com.example.hotelgo.data.DataProfile
import com.example.hotelgo.data.DataRoom
import com.example.hotelgo.firebase.FirebaseAuthHelper.uid
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object FirebaseDatabaseHelper {
    private val database = FirebaseDatabase.getInstance()
    private val hotelRef = database.getReference("hotel")
    private val userRef = database.getReference("user")
    private val profileRef = userRef.child(uid!!).child("profile")
    private val bookingRef = userRef.child(uid!!).child("booking")
    private val TAG = "FirebaseDatabaseHelper"


    fun getHotelData(idhotel:String, hotelCallback: HotelCallback){
        hotelRef.child(idhotel).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                val dataHotel = dataHotel(p0)
                hotelCallback.onLoadHotelData(dataHotel)
            }

        })
    }


    fun getBooking(bookingCallback: BookingCallback) = GlobalScope.launch(Dispatchers.IO){
        bookingRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                val listBooking = arrayListOf<DataBooking>()
                for (i in p0.children){
                    val idHotel = i.child(DataBooking.ID_HOTEL).value?.toString()
                    val jumlahKamar = i.child(DataBooking.JUMLAH_KAMAR).value?.toString()
                    val total = i.child(DataBooking.TOTAL).value?.toString()
                    val uid = i.child(DataBooking.UID).value?.toString()
                    val namaRoom = i.child(DataBooking.NAMA_ROOM).value?.toString()
                    val status = i.child(DataBooking.STATUS).value?.toString()

                    val dataBooking = DataBooking(
                        uid = uid,
                        total = total,
                        jumlahKamaar = jumlahKamar,
                        idHotel = idHotel,
                        namaRoom = namaRoom,
                        status = status
                    )
                    listBooking.add(dataBooking)
                }
                bookingCallback.onLoadBookingData(listBooking)
            }

        })
    }
    fun loadHotel(firebaseHotelCallback: FirebaseHotelCallback) =
        GlobalScope.launch(Dispatchers.IO) {
            val data = arrayListOf<DataHotel?>()
            hotelRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    for (i in p0.children) {
                        val dataHotel = dataHotel(i)
                        data.add(dataHotel)
                    }

                    firebaseHotelCallback.onCallbackDataHotel(data)
                }

            })
        }

    private fun dataHotel(i:DataSnapshot):DataHotel{
        val deskripsi = i.child(DataHotel.DESKRIPSI).value?.toString()
        val alamat = i.child(DataHotel.ALAMAT).value?.toString()
        val email = i.child(DataHotel.EMAIL).value?.toString()
        val harga = i.child(DataHotel.HARGA_MULAI).value?.toString()
        val image = i.child(DataHotel.IMAGE).value?.toString()
        val kodepos = i.child(DataHotel.KODE_POS).value?.toString()
        val mapLat = i.child(DataHotel.MAP_LAT).value?.toString()
        val mapLong = i.child(DataHotel.MAP_LONG).value?.toString()
        val nama = i.child(DataHotel.NAMA).value?.toString()
        val noTelp = i.child(DataHotel.NO_TELP).value?.toString()
        val rating = i.child(DataHotel.RATING).value?.toString()
        val uid = i.child(DataHotel.UID).value?.toString()
        val fasilitasUmum = i.child(DataHotel.FASILITAS_UMUM).value?.toString()

        return DataHotel(
            deskripsi = deskripsi,
            alamat = alamat,
            email = email,
            hargaMulai = harga,
            image = image,
            kodePos = kodepos,
            mapLat = mapLat,
            mapLong = mapLong,
            nama = nama,
            noTelfon = noTelp,
            rating = rating,
            uid = uid,
            fasilitasUmum = fasilitasUmum
        )
    }

    fun loadKamarHotel(uidHotel: String?, firebaseHotelCallback: FirebaseHotelCallback) {
        val data = arrayListOf<DataRoom?>()
        hotelRef.child(uidHotel!!).child("kamar")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    for (i in p0.children) {
                        val deskripsi = i.child(DataRoom.DESKRIPSI).value?.toString()
                        val id = i.child(DataRoom.ID).value?.toString()
                        val harga = i.child(DataRoom.HARGA).value?.toString()
                        val image = i.child(DataRoom.IMAGE).value?.toString()
                        val nama = i.child(DataRoom.NAMA).value?.toString()
                        val jumlahKamar = i.child(DataRoom.JUMLAH_KAMAR).value?.toString()
                        val kamarKosong = i.child(DataRoom.KAMAR_KOSONG).value?.toString()
                        val kapasitas = i.child(DataRoom.KAPASITAS).value?.toString()
                        val room = DataRoom(
                            deskripsi = deskripsi,
                            image = image,
                            harga = harga,
                            jumlahKamar = jumlahKamar,
                            kamarKosong = kamarKosong,
                            kapasitas = kapasitas,
                            nama = nama,
                            id = id
                        )

                        data.add(room)
                    }

                    firebaseHotelCallback.onCallBackRoom(data)
                }

            })
    }



    fun checkBookingData(namaRoom: String, checkBooking: CheckBooking){
        bookingRef.child(namaRoom).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                checkBooking.onAlreadyExist(p0.value != null)
            }

        })

    }
    fun insertBooking(dataBooking: DataBooking, firebaseInfo: FirebaseInfo) =
        GlobalScope.launch(Dispatchers.IO) {
            bookingRef.child(dataBooking.namaRoom!!).setValue(dataBooking)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        firebaseInfo.onCompletedListener(true)
                    } else {
                        firebaseInfo.onCompletedListener(false)
                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, it.message!!)
                }
        }


    fun insertProfile(dataProfile: DataProfile, firebaseInfo: FirebaseInfo) =
        GlobalScope.launch(Dispatchers.IO) {
            profileRef.setValue(dataProfile)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        firebaseInfo.onCompletedListener(true)
                    } else {
                        firebaseInfo.onCompletedListener(false)
                    }
                }
                .addOnFailureListener {
                    Log.i(TAG, it.message!!)
                }
        }


    fun getProfileData(firebaseProfileCallback: FirebaseProfileCallback){
        profileRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                val dataProfile = dataProfile(p0)
                firebaseProfileCallback.onLoadData(dataProfile)
            }

        })
    }

    private fun dataProfile(p0:DataSnapshot):DataProfile{
        val alamat = p0.child(DataProfile.ALAMAT).value?.toString()
        val email = p0.child(DataProfile.EMAIL).value?.toString()
        val jk = p0.child(DataProfile.JK).value?.toString()
        val nama = p0.child(DataProfile.NAMA).value?.toString()
        val telfon = p0.child(DataProfile.TELFON).value?.toString()
        val ttl = p0.child(DataProfile.TTL).value?.toString()
        val uid = p0.child(DataProfile.UID).value?.toString()
        val image = p0.child(DataProfile.IMAGE).value?.toString()
        return DataProfile(
            uid = uid,
            nama = nama,
            email = email,
            alamat = alamat,
            jenisKelamin = jk,
            tanggalLahir = ttl,
            telfon = telfon,
            image = image
        )
    }
}