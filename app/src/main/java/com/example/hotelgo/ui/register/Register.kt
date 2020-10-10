package com.example.hotelgo.ui.register

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
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

import com.example.hotelgo.R
import com.example.hotelgo.firebase.FirebaseAuthHelper.createUser
import com.example.hotelgo.firebase.FirebaseStorageHelper
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.register_fragment.*

class Register : Fragment(){

    companion object {
        fun newInstance() = Register()
    }

    private val TAG = "Register"

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
    }


    private fun normalizeButton(){
        Handler().postDelayed({
            btn_register_register.revertAnimation()
        }, 2000)
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        linear_bottom_register.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_register_to_login)
        }
        btn_register_register?.setOnClickListener {
            btn_register_register.startAnimation()
            val email = edt_register_email.text.toString().trim()
            val password = edt_register_password.text.toString()
            val repeatPassword = edt_register_repeatPassword.text.toString()
            if(password != repeatPassword){
                Toast.makeText(this.context, "Password is not correct", Toast.LENGTH_SHORT).show()
                normalizeButton()
            }else {
                if (email.isNotEmpty() || password.isNotEmpty() || repeatPassword.isNotEmpty()) {
                    createUser(email, password)
                        .addOnSuccessListener {
                            view?.findNavController()?.navigate(R.id.action_register_to_login)
                            Log.i(TAG, "Register user success")
                            showToast("Register success")
                            clearRegisterEditText()
                            normalizeButton()
                        }
                        .addOnFailureListener {
                            Log.i(TAG, "Register user failed ${it.message}")
                            btn_register_register.doneLoadingAnimation(
                                ContextCompat.getColor(context!!, R.color.colorPrimary),
                                resources.getDrawable(R.drawable.ic_close).toBitmap()
                            )
                            showToast("Register Failed")
                            clearRegisterEditText()
                            normalizeButton()
                        }
                }else{
                    Toast.makeText(this.context, "Form is empty!", Toast.LENGTH_LONG).show()
                    normalizeButton()
                }
            }
        }
    }

    private fun showToast(message:String){
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }
    private fun clearRegisterEditText(){
        edt_register_email.setText("")
        edt_register_password.setText("")
        edt_register_repeatPassword.setText("")
    }

}
