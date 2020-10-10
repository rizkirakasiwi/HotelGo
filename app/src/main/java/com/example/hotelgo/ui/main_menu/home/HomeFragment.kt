package com.example.hotelgo.ui.main_menu.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.hotelgo.R
import com.example.hotelgo.controller.AdapterHome
import com.example.hotelgo.data.DataHotel
import com.example.hotelgo.data.DataRoom
import com.example.hotelgo.firebase.FirebaseDatabaseHelper.loadHotel
import com.example.hotelgo.firebase.FirebaseStorageHelper
import com.example.hotelgo.firebase.FirebaseHotelCallback
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }


    private val TAG = "HomeFragment"
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        Log.i(TAG, "oncreateview")
        return inflater.inflate(R.layout.home_fragment, container, false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "oncreate")
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        loadHotel(object : FirebaseHotelCallback {
            override fun onCallbackDataHotel(data: ArrayList<DataHotel?>) {
                viewModel.setHotelData(data)
            }

            override fun onCallBackRoom(data: ArrayList<DataRoom?>) {}

        })

    }


    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onresume")
        viewModel.hotelData.observe(this, Observer {
            Log.i(TAG, it.toString())
            recycler_homeFragment.adapter =
                AdapterHome(it)
        })
    }

}
