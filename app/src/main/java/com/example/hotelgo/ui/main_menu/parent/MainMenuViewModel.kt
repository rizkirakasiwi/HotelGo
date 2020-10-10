package com.example.hotelgo.ui.main_menu.parent

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hotelgo.controller.FragmentNow

class MainMenuViewModel:ViewModel() {
    val fragment : LiveData<Fragment?> get() = FragmentNow.fragment
}