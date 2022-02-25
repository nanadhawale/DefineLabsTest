package com.definelabs.definelabstest.activities.dashboard

import android.app.ProgressDialog
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.ui.AppBarConfiguration
import com.definelabs.definelabstest.R
import com.definelabs.definelabstest.database.SqliteManager
import com.definelabs.definelabstest.fragments.AllMatchesFragment
import com.definelabs.definelabstest.fragments.FavoriteMatchesFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var db:SqliteManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        nav_view.setNavigationItemSelectedListener(this)
        replaceFragment(R.id.nav_all_matches)
        db = SqliteManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        replaceFragment(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(item: Int) {
        var fragment: Fragment? = null
        when (item) {
            R.id.nav_all_matches -> {
                fragment = AllMatchesFragment()
                nav_view.menu.getItem(0).isChecked = true;
                nav_view.menu.getItem(1).isChecked = false;
            }
            R.id.nav_stored_matches -> {
                fragment = FavoriteMatchesFragment()
                nav_view.menu.getItem(1).isChecked = true;
                nav_view.menu.getItem(0).isChecked = false;
            }
        }

        if (fragment != null) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.replace(R.id.nav_host_fragment, fragment)
            ft.addToBackStack(null)
            ft.commit()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
    }


    fun drawerHandler(view:View){
        drawer_layout.openDrawer(Gravity.LEFT)
    }

}