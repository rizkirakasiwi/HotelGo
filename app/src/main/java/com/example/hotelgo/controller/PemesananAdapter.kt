package com.example.hotelgo.controller

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelgo.R
import com.example.hotelgo.data.DataRoom
import com.example.hotelgo.firebase.CheckBooking
import com.example.hotelgo.firebase.FirebaseDatabaseHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pemesanan_ui.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PemesananAdapter(private val data: ArrayList<DataRoom?>, val fm: FragmentManager) :
    RecyclerView.Adapter<MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pemesanan_ui, parent, false)
        val viewHolder = MyViewHolder(view)
        view.btn_pemesanan_pesan.setOnClickListener {
            FirebaseDatabaseHelper.checkBookingData(data[viewHolder.adapterPosition]?.id!!, object : CheckBooking{
                override fun onAlreadyExist(isExist: Boolean?) {
                    if (isExist!!){
                        AlertDialog.Builder(view.context)
                            .setTitle("Pesan")
                            .setMessage("Anda telah memesan kamar ini sebelumnya, apakah anda ingin mengedit pesanan anda?")
                            .setPositiveButton("Iya"){_ , _->
                                val alert =
                                    PemesananAlert()
                                val bundle = bundleOf("pemesanan" to data[viewHolder.adapterPosition])
                                alert.arguments = bundle
                                alert.isCancelable = false
                                alert.show(fm, "Pemesanan_Alert")
                            }
                            .setNegativeButton("Batalkan"){dialog, which ->
                            }
                            .create()
                            .show()
                    }else{
                        val alert =
                            PemesananAlert()
                        val bundle = bundleOf("pemesanan" to data[viewHolder.adapterPosition])
                        alert.arguments = bundle
                        alert.isCancelable = false
                        alert.show(fm, "Pemesanan_Alert")
                    }
                }

            })

        }
        return viewHolder
    }

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val view = holder.itemView
        Log.i("PemesananAdapter", data[position]?.image!!)
        view.img_pemesanan_image.load(data[position]?.image)
        view.txt_pemesanan_deskripsi.text = data[position]?.deskripsi
        view.txt_pemesanan_harga.text = view.context.getString(
            R.string.harga,
            Money.getMoneyFormat(data[position]?.harga?.toInt())
        )
        view.txt_pemesanan_kamarkosong.text =
            view.context.getString(R.string.kamar_sisa, data[position]?.kamarKosong)
        view.txt_pemesanan_nama.text = data[position]?.nama
        view.txt_pemesanan_kapasitas.text =
            view.context.getString(R.string.kapasitas, data[position]?.kapasitas)
    }

    private fun ImageView.load(url: String?) = GlobalScope.launch(Dispatchers.Main) {
        Picasso.get().load(url).resize(100, 100).centerCrop().into(this@load)
    }

}