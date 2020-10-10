package com.example.hotelgo.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.hotelgo.R

class MainActivity : AppCompatActivity() {

    //menyediakan variable navigation controller dan appbar configuration
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mengecek phone permission jika belum diberikan izin untuk mengakses telfon maka akan dikirim request permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            val permission = arrayOf(android.Manifest.permission.CALL_PHONE)
            ActivityCompat.requestPermissions(this, permission, 0)
        }

        //menginisialisasi nav controller
        navController = findNavController(R.id.nav_host_fragment)

        //menginisiasi appbar configurasi
        appBarConfiguration = AppBarConfiguration(setOf(R.id.mainMenu))

        //menempelkan navigation component ke  fragment
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    //membuat tombol back
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}
