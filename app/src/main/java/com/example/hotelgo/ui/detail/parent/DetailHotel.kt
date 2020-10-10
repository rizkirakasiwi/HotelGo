package com.example.hotelgo.ui.detail.parent

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import com.example.hotelgo.R
import com.example.hotelgo.controller.AdapterHome
import com.example.hotelgo.controller.DetialViewPagerAdapter
import com.example.hotelgo.controller.PassDataHotel
import com.example.hotelgo.data.DataHotel
import com.example.hotelgo.location.LocationHelper
import com.example.hotelgo.telefon.CallHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_hotel_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailHotel : Fragment() {

    companion object {
        fun newInstance() = DetailHotel()
        private const val TAG = "DetailHotel"
    }

    private lateinit var viewModel: DetailHotelViewModel
    private lateinit var dataHotel: DataHotel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_hotel_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            dataHotel = arguments?.getParcelable(AdapterHome.HOTEL_BUNDLE_CODE)!!
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailHotelViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        PassDataHotel.setHotelData(dataHotel)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.detailHotel)
        Log.i(TAG, dataHotel.toString())
        img_detail_banner.load(dataHotel.image)
        txt_detail_alamat.text = dataHotel.alamat
        txt_detail_nama.text = dataHotel.nama
        txt_detail_rating.text = dataHotel.rating
        rating_detail.rating = dataHotel.rating!!.toFloat()/2f
        img_detail_goLocation.setOnClickListener {view->
            LocationHelper(this.context!!).getLocation(dataHotel.mapLat, dataHotel.mapLong)
        }
        img_detail_telf.setOnClickListener {view->
            CallHelper(this.context!!).getCall(dataHotel.noTelfon)
        }
    }

    private fun ImageView.load(url:String?) = GlobalScope.launch(Dispatchers.Main){
        Picasso.get().load(url).into(this@load)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager_detail.adapter =
            DetialViewPagerAdapter(
                childFragmentManager
            )
        tab_detail.setupWithViewPager(viewPager_detail)
    }

}

