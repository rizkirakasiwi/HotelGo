package com.example.hotelgo.ui.main_menu.booking

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.hotelgo.R
import com.example.hotelgo.controller.BookingAdapter
import com.example.hotelgo.data.DataBooking
import com.example.hotelgo.data.DataHotel
import com.example.hotelgo.data.DataRoom
import com.example.hotelgo.firebase.BookingCallback
import com.example.hotelgo.firebase.FirebaseDatabaseHelper
import com.example.hotelgo.firebase.FirebaseHotelCallback
import com.example.hotelgo.firebase.RoomDataCallback
import kotlinx.android.synthetic.main.booking_fragment.*

class BookingOwnerFragment : Fragment() {

    companion object {
        fun newInstance() = BookingOwnerFragment()
        private const val TAG = "BookingOwnerFragment"
    }

    private lateinit var viewModel: BookingOwnerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.booking_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookingOwnerViewModel::class.java)
        FirebaseDatabaseHelper.getBooking(object : BookingCallback{
            override fun onLoadBookingData(data: ArrayList<DataBooking>?) {
                viewModel.setBookingData(data)
            }

        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.bookingData.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, it.toString())
            recycler_booking.adapter = BookingAdapter(it)
        })
    }


}
