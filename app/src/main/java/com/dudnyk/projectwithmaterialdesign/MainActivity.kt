package com.dudnyk.projectwithmaterialdesign

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dudnyk.projectwithmaterialdesign.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar


class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var toolbar: MaterialToolbar
    private  lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initObjects()
        setUpToolBar()
        setUpBottomNavigation()
        setUpFragment()
        setUpFab()
    }

    companion object{
        const val FRAGMENT = "FRAGMENT"
        const val FRAGMENT_PROFILE  = "FRAGMENT_PROFILE"
        const val FRAGMENT_CATEGORY = "FRAGMENT_CATEGORY"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    private fun initObjects() {
        toolbar = mainBinding.mainToolbar.myToolbar
        sp = getSharedPreferences(RegisterActivity.LOGIN, Context.MODE_PRIVATE)
    }

    private fun setUpFragment() {
        when(intent.getStringExtra(FRAGMENT)) {
            FRAGMENT_PROFILE -> mainBinding.bottomNavigation.selectedItemId = R.id.b_nav_profile
            else -> mainBinding.bottomNavigation.selectedItemId = R.id.b_nav_categories
        }
    }

    private fun setUpToolBar() {
        setSupportActionBar(toolbar)
    }

    private fun setUpFab() {
        mainBinding.fab.setOnClickListener {
            logOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        if (isLoggedIn()) {
            mainBinding.fab.hide()
        }
    }

    fun isLoggedIn(): Boolean {
        return sp.getInt(RegisterActivity.USER_ID, -1) != -1
    }

    fun logOut() {
        sp.edit().remove(RegisterActivity.USER_ID).apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_app -> {
                startActivity(Intent(this, AppActivity::class.java))
                true
            }
            R.id.about_author -> {
                startActivity(Intent(this, AuthorActivity::class.java))
                true
            }
            R.id.menu_actions -> {
                //TODO SOME MENU OPTIONS
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpBottomNavigation() {
        mainBinding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.b_nav_categories -> {
                    startShopFragments()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.b_nav_help -> {
                    startHelpFragment()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.b_nav_profile -> {
                    startProfilefragment()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }


    private fun startHelpFragment() {
        toolbar.title = resources.getString(R.string.help)
        // TODO add help activity (if necessary)
        // loadFragment()
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(fragment::class.java.simpleName)
        transaction.commit()
    }

    private fun startProfilefragment() {
        toolbar.title = resources.getString(R.string.profile)
        loadFragment(ProfileFragment.newInstance())
    }

    private fun startShopFragments() {
        toolbar.title = resources.getString(R.string.categories_title)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CategoryListFragment.newInstance(), CategoryListFragment.TAG)
            .commit()

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                toolbar.setNavigationOnClickListener{ onBackPressed() }
            } else {
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            }
        }
    }
}
