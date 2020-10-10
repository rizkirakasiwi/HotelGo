package com.example.hotelgo.controller

import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelgo.R
import com.example.hotelgo.data.DataBooking
import com.example.hotelgo.data.DataHotel
import com.example.hotelgo.data.DataRoom
import com.example.hotelgo.firebase.FirebaseDatabaseHelper
import com.example.hotelgo.firebase.FirebaseHotelCallback
import com.example.hotelgo.firebase.HotelCallback
import com.example.hotelgo.location.LocationHelper
import kotlinx.android.synthetic.main.booking_layout_item.view.*

class BookingAdapter(private val dataBooking : ArrayList<DataBooking>?):RecyclerView.Adapter<MyViewHolder>(){

    //membuat layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //menempelkan layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.booking_layout_item, parent, false)
        val viewHolder = MyViewHolder(view)

        //memunculkan lokasi saat di click
        view.img_bookinglayout_lokasi.setOnClickListener {
            FirebaseDatabaseHelper.getHotelData(dataBooking!![viewHolder.adapterPosition].idHotel!!, object : HotelCallback{
                override fun onLoadHotelData(data: DataHotel) {
                    LocationHelper(view.context).getLocation(data.mapLat, data.mapLong)
                }
            })
        }
        return viewHolder
    }

    //jumlah list
    override fun getItemCount(): Int = dataBooking!!.count()


    //memberikan data kepada text
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val view = holder.itemView
        view.txt_bookingItem_jenisKamar.text =
            view.context.getString(R.string.jenis_kamar_booking, dataBooking?.get(position)?.namaRoom)
        view.txt_bookingItem_jumlahKamar.text =
            view.context.getString(R.string.jumlah_kamar_booking, dataBooking?.get(position)?.jumlahKamaar)

        val duit = Money.getMoneyFormat(dataBooking!![position].total?.toInt())
        view.txt_bookingItem_total.text = view.context.getString(R.string.total_harga_booking,duit)
        view.txt_status.text = view.context.getString(R.string.status, dataBooking[position].status)


        //memngambil data dari firebase
        FirebaseDatabaseHelper.getHotelData(dataBooking[position].idHotel!!, object : HotelCallback{
            override fun onLoadHotelData(data: DataHotel) {
                view.txt_bookingItem_namaHotel.text =
                    view.context.getString(R.string.nama_hotel, data.nama)
            }
        })

    }

}