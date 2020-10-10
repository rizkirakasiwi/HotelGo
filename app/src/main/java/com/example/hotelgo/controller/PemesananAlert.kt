package com.example.hotelgo.controller

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.hotelgo.R
import com.example.hotelgo.data.DataBooking
import com.example.hotelgo.data.DataRoom
import com.example.hotelgo.firebase.FirebaseAuthHelper.uid
import com.example.hotelgo.firebase.FirebaseDatabaseHelper.insertBooking
import com.example.hotelgo.firebase.FirebaseInfo
import kotlinx.android.synthetic.main.pemesanan_alert.*


class PemesananAlert : DialogFragment() {

    private lateinit var dataRoom: DataRoom
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pemesanan_alert,container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            dataRoom = arguments?.getParcelable("pemesanan")!!
        }
    }



    override fun onResume() {
        super.onResume()
        val harga = dataRoom.harga?.toInt()
        val kamar = dataRoom.nama
        txt_pemesananAlert_jenisKamar.text = kamar
        txt_pemesananAlert_total.text =
            Money.getMoneyFormat(harga)
        edt_pemesananAlert_jumlahKamar.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val jumlahHotel = s.toString()
                if (jumlahHotel.isNotEmpty()){
                    val total = harga!! * jumlahHotel.toInt()
                    txt_pemesananAlert_total.text =
                        Money.getMoneyFormat(total)
                }else{
                    txt_pemesananAlert_total.text =
                        Money.getMoneyFormat(harga)
                }
            }

        })

        btn_pemesananAlert_cancel.setOnClickListener {
            dismiss()
        }

        btn_pemesananAlert_booking.setOnClickListener {
            progress_alert.visibility = View.VISIBLE
            PassDataHotel.hotelData.observe(this, Observer {
                val total = harga!! * edt_pemesananAlert_jumlahKamar.text.toString().toInt()
                val booking = DataBooking(
                    uid = uid,
                    idHotel = it.uid,
                    jumlahKamaar = edt_pemesananAlert_jumlahKamar.text.toString(),
                    namaRoom = dataRoom.id,
                    total = total.toString(),
                    status = "Belum Dibayar"
                )
                insertBooking(booking, object : FirebaseInfo{
                    override fun onCompletedListener(isSuccess: Boolean) {
                        if (isSuccess) {
                            showToast(getString(R.string.booking_success))
                            dismiss()
                        }else{
                            showToast(getString(R.string.booking_failed))
                        }
                        progress_alert.visibility = View.GONE
                    }

                })
            })
        }
    }


    private fun showToast(message:String){
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }


}