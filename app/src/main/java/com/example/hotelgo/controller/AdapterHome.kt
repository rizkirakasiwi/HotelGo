package com.example.hotelgo.controller

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelgo.R
import com.example.hotelgo.data.DataHotel
import com.example.hotelgo.location.LocationHelper
import com.example.hotelgo.telefon.CallHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.home_layout_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

//menampung layout untuk home
class AdapterHome(private val dataHotel: ArrayList<DataHotel?>) :
    RecyclerView.Adapter<MyViewHolder>() {

    //code untuk mengirim data
    companion object{
        const val HOTEL_BUNDLE_CODE = "hotelData"
    }

    //tag
    private val TAG = "HomeAdapter"

    //menempelkan layout ke adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val homeViewHolder =
            MyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.home_layout_item,
                    parent,
                    false
                )
            )
        val view = homeViewHolder.itemView


        //ketika tombol lokasi di klick akan memanggil fungsi map
        view.img_homeLayout_goLocation.setOnClickListener {
            //fungsi map
            LocationHelper(view.context).getLocation(
                dataHotel[homeViewHolder.adapterPosition]?.mapLat,
                dataHotel[homeViewHolder.adapterPosition]?.mapLong
            )
        }

        //ketika tombol telfon di klik akan memanggil fungsi telfon
        view.img_homeLayout_telf.setOnClickListener {
            //menampilkan alert dialog
            AlertDialog.Builder(view.context)
                .setTitle(view.context.getString(R.string.telefon))
                .setMessage("Apakah anda ingin menelfon?")
                .setPositiveButton("Iya"){_,_->
                    //ketika user mengklik positif button akan mulai menelfon
                    CallHelper(view.context).getCall(
                        dataHotel[homeViewHolder.adapterPosition]?.noTelfon
                    )
                }
                .setNegativeButton("Batalkan"){_,_->}
                .create()
                .show()

        }

        //ketika view hotel di klick akan pindah ke detail hotel
        view.setOnClickListener {
            val bundle = bundleOf(HOTEL_BUNDLE_CODE to dataHotel[homeViewHolder.adapterPosition])
            view.findNavController().navigate(R.id.action_mainMenu_to_detailHotel, bundle)
        }
        return homeViewHolder
    }

    //menentukan jumlah list pada recyclerview
    override fun getItemCount() = dataHotel.count()

    //menampilkan text pada view layout
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val view = holder.itemView
        view.img_homelayout_image.load(dataHotel[position]?.image!!)
        view.txt_homeLayout_nama.text = dataHotel[position]?.nama
        view.txt_homeLayout_lokasi.text = dataHotel[position]?.alamat
        val rating = dataHotel[position]?.rating?.toFloat()
        view.txt_homeLayout_rating.text = rating.toString()
        val harga = Money.getMoneyFormat(dataHotel[position]?.hargaMulai?.toInt())
        view.txt_home_harga.text =
            view.context.getString(R.string.harga_dimulai_dari, harga.toString())
        view.rating_homeLayout.rating = rating!!
    }

    //load image dari internet
    private fun ImageView.load(imagePath: String) = GlobalScope.launch(Dispatchers.Main) {
        Picasso.get().load(imagePath).into(this@load)
    }

}