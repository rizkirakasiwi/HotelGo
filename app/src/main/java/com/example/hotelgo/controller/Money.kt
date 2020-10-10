package com.example.hotelgo.controller

import java.text.NumberFormat
import java.util.*

object Money {
    //mengkonversi integer ke currency format
    fun getMoneyFormat(number:Int?):String?{
        val locale = Locale("in", "ID")
        val moneyFormat = NumberFormat.getCurrencyInstance(locale)
        return moneyFormat.format(number).toString()
    }
}