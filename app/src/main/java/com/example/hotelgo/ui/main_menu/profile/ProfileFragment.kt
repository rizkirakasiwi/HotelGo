package com.example.hotelgo.ui.main_menu.profile

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap

import com.example.hotelgo.R
import com.example.hotelgo.data.DataProfile
import com.example.hotelgo.firebase.*
import com.example.hotelgo.firebase.FirebaseAuthHelper.uid
import com.example.hotelgo.firebase.FirebaseDatabaseHelper.insertProfile
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ProfileFragment : Fragment(), View.OnClickListener {


    companion object {
        fun newInstance() = ProfileFragment()
        private const val TAG = "ProfileFragment"
        const val REQUEST_TAKE_PHOTO = 1
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var image: Uri
    private lateinit var picture: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        FirebaseDatabaseHelper.getProfileData(object : FirebaseProfileCallback {
            override fun onLoadData(data: DataProfile) {
                viewModel.setDataProfile(data)
            }

        })
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

    }


    override fun onResume() {
        super.onResume()
        edt_profile_email.setText(FirebaseAuthHelper.getEmail())

        viewModel.path.observe(this, androidx.lifecycle.Observer { uri ->
            Log.i(TAG, "$uri")
            img_profile_image.setImageURI(uri)
            image = uri
        })


        viewModel.dataProfile.observe(this, androidx.lifecycle.Observer {
            if (it.uid != null) {
                Log.i(TAG, it.toString())
                picture = it.image!!
                edt_profile_namaLengkap.setText(it.nama)
                edt_profile_alamat.setText(it.alamat)
                edt_profile_jk.setText(it.jenisKelamin)
                edt_profile_ttl.setText(it.tanggalLahir)
                edt_profile_notelp.setText(it.telfon)
                if (!::image.isInitialized) {
                    GlobalScope.launch(Dispatchers.Main) {
                        Picasso.get().load(it?.image).into(img_profile_image)
                    }
                }
            }
        })


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img_profile_editUmum?.setOnClickListener(this)
        img_profile_editakun?.setOnClickListener(this)
        img_profile_editkontak?.setOnClickListener(this)
        img_profile_changeProfile?.setOnClickListener(this)
        btn_profile_simpanPerubahan?.setOnClickListener(this)
        edt_profile_jk?.setOnClickListener(this)
        edt_profile_ttl?.setOnClickListener(this)
    }

    private fun formEmpty(): Boolean {
        val nama = edt_profile_namaLengkap.text.toString()
        val email = edt_profile_email.text.toString()
        val alamat = edt_profile_alamat.text.toString()
        val jk = edt_profile_jk.text.toString()
        val ttl = edt_profile_ttl.text.toString()
        val telfon = edt_profile_notelp.text.toString()
        return !(nama.isEmpty() && email.isEmpty() && alamat.isEmpty() && jk.isEmpty() && ttl.isEmpty()
                && telfon.isEmpty() && nama == getString(R.string.belum_diisi)
                && email == getString(R.string.belum_diisi)
                && alamat == getString(R.string.belum_diisi) && jk == getString(R.string.belum_diisi)
                && telfon == getString(R.string.belum_diisi))
    }


    private fun jenisKelaminHelper(): AlertDialog {
        val jk = arrayOf("Laki - laki", "Perempuan")
        return AlertDialog.Builder(this.context!!)
            .setTitle(getString(R.string.jenis_kelamin))
            .setItems(jk) { dialog, which ->
                edt_profile_jk.setText(jk[which])
            }
            .create()


    }

    private fun showToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }

    @SuppressLint("SetTextI18n")
    private fun calendarHelper() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this.context!!,
            DatePickerDialog.OnDateSetListener { _, year, _, dayOfMonth ->
                // Display Selected date in TextView
                edt_profile_ttl.setText("$dayOfMonth/$month/$year")
            },
            year,
            month,
            day
        )
        dpd.show()
    }


    private fun umumHelper(isDone: Boolean) {
        edt_profile_namaLengkap.isEnabled = !isDone
        edt_profile_jk.isEnabled = !isDone
        edt_profile_ttl.isEnabled = !isDone
        edt_profile_alamat.isEnabled = !isDone
    }

    private fun kontakHelper(isDone: Boolean) {
        edt_profile_email.isEnabled = !isDone
        edt_profile_notelp.isEnabled = !isDone
    }

    private fun akunHelper(isDone: Boolean) {
        edt_profile_password.isEnabled = !isDone
        textInputLayout_profile_ulangiPass.visibility = if (isDone) View.GONE else View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        disableAll()
    }

    private fun disableAll(disable: Boolean = true) {
        kontakHelper(disable)
        akunHelper(disable)
        umumHelper(disable)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            viewModel.setPath(data?.data!!)
        }
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_profile_editUmum -> {
                textInputLayout_profile_jk.helperText = getString(R.string.doubleclick)
                textInputLayout_profile_ttl.helperText = getString(R.string.doubleclick)
                umumHelper(false)
            }
            R.id.img_profile_editakun -> akunHelper(false)
            R.id.img_profile_editkontak -> kontakHelper(false)
            R.id.img_profile_changeProfile -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_TAKE_PHOTO)
            }
            R.id.btn_profile_simpanPerubahan -> {
                if (!formEmpty()) {
                    showToast("Form tidak boleh ada yang kosong")
                } else {
                    simpanPerubahan()
                }


            }

            R.id.edt_profile_ttl -> {
                calendarHelper()
                textInputLayout_profile_ttl.helperText = null
            }
            R.id.edt_profile_jk -> {
                jenisKelaminHelper().show()
                textInputLayout_profile_jk.helperText = null
            }
        }
    }

    private fun normalizeButton(){
        Handler().postDelayed({
            btn_profile_simpanPerubahan.revertAnimation()
        }, 2000)
    }

    private fun simpanPerubahan() {
        disableAll()
        btn_profile_simpanPerubahan.startAnimation()
        val uid = uid
        val nama = edt_profile_namaLengkap.text.toString()
        val email = edt_profile_email.text.toString()
        val alamat = edt_profile_alamat.text.toString()
        val jk = edt_profile_jk.text.toString()
        val ttl = edt_profile_ttl.text.toString()
        val telfon = edt_profile_notelp.text.toString()
        if (::image.isInitialized) {
            FirebaseStorageHelper.insertImageUserToStorage(image, object : FirebaseStorageInfo {
                override fun onCompletedUploadListener(isSuccess: Boolean, image: String?) {
                    if (isSuccess) {
                        val dataProfile = DataProfile(
                            uid = uid,
                            nama = nama,
                            email = email,
                            alamat = alamat,
                            jenisKelamin = jk,
                            tanggalLahir = ttl,
                            telfon = telfon,
                            image = image
                        )
                        insertProfile(dataProfile, object : FirebaseInfo {
                            override fun onCompletedListener(isSuccess: Boolean) {
                                if (isSuccess) {
                                    btn_profile_simpanPerubahan.doneLoadingAnimation(
                                        ContextCompat.getColor(context!!, R.color.colorPrimary),
                                        resources.getDrawable(R.drawable.ic_check).toBitmap()
                                    )
                                    normalizeButton()
                                    showToast(getString(R.string.berhasil_disimpan))
                                } else {
                                    btn_profile_simpanPerubahan.doneLoadingAnimation(
                                        ContextCompat.getColor(context!!, R.color.colorPrimary),
                                        resources.getDrawable(R.drawable.ic_close).toBitmap()
                                    )
                                    normalizeButton()
                                    showToast(getString(R.string.gagal_disimpan))
                                }
                            }
                        })
                    } else {
                        Log.i(TAG, "Upload image gagal")
                    }
                }

            })
        } else {
            val dataProfile = DataProfile(
                uid = uid,
                nama = nama,
                email = email,
                alamat = alamat,
                jenisKelamin = jk,
                tanggalLahir = ttl,
                telfon = telfon,
                image = picture
            )
            insertProfile(dataProfile, object : FirebaseInfo {
                override fun onCompletedListener(isSuccess: Boolean) {
                    if (isSuccess) {
                        btn_profile_simpanPerubahan?.doneLoadingAnimation(
                            ContextCompat.getColor(context!!, R.color.colorPrimary),
                            resources.getDrawable(R.drawable.ic_check).toBitmap()
                        )
                        normalizeButton()
                        showToast(getString(R.string.berhasil_disimpan))
                    } else {
                        btn_profile_simpanPerubahan?.doneLoadingAnimation(
                            ContextCompat.getColor(context!!, R.color.colorPrimary),

                            resources.getDrawable(R.drawable.ic_close).toBitmap()
                        )
                        normalizeButton()
                        showToast(getString(R.string.gagal_disimpan))
                    }
                }

            })
        }
    }


}
