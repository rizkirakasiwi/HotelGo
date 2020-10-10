package com.example.hotelgo.ui.detail.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.hotelgo.R
import com.example.hotelgo.controller.PassDataHotel
import com.example.hotelgo.location.LocationHelper
import com.example.hotelgo.telefon.CallHelper
import kotlinx.android.synthetic.main.booking_layout_item.*
import kotlinx.android.synthetic.main.detail_hotel_fragment.*
import kotlinx.android.synthetic.main.detail_layout_fragment.*


class DetailLayout : Fragment() {

    companion object {
        fun newInstance() = DetailLayout()
    }



    private val TAG = "DetailLayout"
    private lateinit var viewModel: DetailLayoutViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_layout_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailLayoutViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.dataHotel.observe(this, Observer {
            Log.i(TAG, it.toString())
            txt_detail_detail.text = getString(R.string.deskripsi, it.deskripsi)
            val fasilitasUmum = it.fasilitasUmum?.split(",")?.map { it.trim() }
            var fasilitasText = "Fasilitas:\n"
            for (i in 0 until fasilitasUmum!!.count()-1){
                fasilitasText += "${i+1}. ${fasilitasUmum[i]}\n"
            }
            txt_detail_fasilitas.text = fasilitasText

        })


    }


}
