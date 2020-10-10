package com.example.hotelgo.ui.main_menu.parent

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager

import com.example.hotelgo.R
import com.example.hotelgo.ui.main_menu.booking.BookingOwnerFragment
import com.example.hotelgo.ui.main_menu.home.HomeFragment
import com.example.hotelgo.ui.main_menu.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.main_menu_fragment.*
import kotlin.system.exitProcess

class MainMenu : Fragment() {

    companion object {
        fun newInstance() = MainMenu()
        private const val TAG = "MainMenu"
    }

    private lateinit var viewModel: MainMenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.main_menu_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainMenuViewModel::class.java)
    }

    private fun setFragment(fragment: Fragment){
        childFragmentManager.commitNow(allowStateLoss = true) {
            replace(R.id.frame_main, fragment)
        }
    }
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.item_menu_home -> {
                setFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.item_menu_booking -> {
                setFragment(BookingOwnerFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.item_menu_profile -> {
                setFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener  true
            }

        }

        false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_setting,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when(item.itemId){
            R.id.item_menu_setting -> {
                true
            }
            R.id.item_menu_logout -> {
                FirebaseAuth.getInstance().signOut()
                if (FirebaseAuth.getInstance().currentUser == null) {
                    view?.findNavController()?.navigate(R.id.action_mainMenu_to_login)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private var exit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment(HomeFragment())
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav_mainMenu.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        activity?.onBackPressedDispatcher?.addCallback(this) {
            if(exit) {
                AlertDialog.Builder(context!!)
                activity?.moveTaskToBack(true)
                exitProcess(-1)
            }else{
                Toast.makeText(context!!, "Press again for close the apps", Toast.LENGTH_SHORT).show()
                exit = true
            }
        }
    }

}
