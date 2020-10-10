package com.example.hotelgo.ui.detail.pemesanan

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.hotelgo.R
import com.example.hotelgo.controller.PassDataHotel
import com.example.hotelgo.controller.PemesananAdapter
import com.example.hotelgo.data.DataHotel
import com.example.hotelgo.data.DataRoom
import com.example.hotelgo.firebase.FirebaseDatabaseHelper
import com.example.hotelgo.firebase.FirebaseDatabaseHelper.loadKamarHotel
import com.example.hotelgo.firebase.FirebaseHotelCallback
import com.example.hotelgo.firebase.FirebaseStorageHelper
import kotlinx.android.synthetic.main.pemesanan_fragment.*

class Pemesanan : Fragment() {

    companion object {
        fun newInstance() = Pemesanan()
        private const val TAG = "Pemesanan"
    }

    private lateinit var viewModel: PemesananViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pemesanan_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PemesananViewModel::class.java)
        PassDataHotel.hotelData.observe(viewLifecycleOwner, Observer {
            loadKamarHotel(it.uid, object : FirebaseHotelCallback{
                override fun onCallbackDataHotel(data: ArrayList<DataHotel?>) {}

                override fun onCallBackRoom(data: ArrayList<DataRoom?>) {
                   viewModel.setRoomData(data)
                }

            })
        })

    }

    override fun onResume() {
        super.onResume()

        viewModel.dataRoomData.observe(this, Observer { data->
            recycler_detail_pemesanan.adapter = PemesananAdapter(data, childFragmentManager)
        })
    }


}
