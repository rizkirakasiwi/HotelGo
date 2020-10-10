package com.example.hotelgo.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.example.hotelgo.R
import com.example.hotelgo.firebase.FirebaseAuthHelper.isLogin
import com.example.hotelgo.firebase.FirebaseAuthHelper.loginAuth
import com.example.hotelgo.firebase.FirebaseStorageHelper
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.register_fragment.*

class Login : Fragment() {

    companion object {
        fun newInstance() = Login()
    }

    private lateinit var viewModel: LoginViewModel
    private val TAG = "Login"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        val isLogin = isLogin()
        if(isLogin != null){
            view?.findNavController()?.navigate(R.id.action_login_to_mainMenu)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()

        btn__login_login.setOnClickListener {
            btn__login_login.startAnimation()
            val email = edt_login_email.text.toString().trim()
            val password = edt_login_password.text.toString()
            loginAuth(email, password)
                .addOnSuccessListener {
                    btn__login_login.revertAnimation()
                    view?.findNavController()?.navigate(R.id.action_login_to_mainMenu)
                    Log.i(TAG, "Berhasil login")
                }
                .addOnFailureListener {
                    btn__login_login.doneLoadingAnimation(
                        ContextCompat.getColor(context!!, R.color.colorPrimary),
                        resources.getDrawable(R.drawable.ic_close).toBitmap()
                    )
                    btn__login_login.revertAnimation()
                    Toast.makeText(this.context, "Login gagal ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        txt_login_tryregister.setOnClickListener {
            this.findNavController().navigate(R.id.action_login_to_register)
        }

    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

}
