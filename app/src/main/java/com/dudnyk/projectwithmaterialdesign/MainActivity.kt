package com.dudnyk.projectwithmaterialdesign

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.dudnyk.projectwithmaterialdesign.Preferences.UserPreferences
import com.dudnyk.projectwithmaterialdesign.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var toolbar: MaterialToolbar
    private  lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initObjects()
        setUpToolBar()
        setUpBottomNavigation()
        setUpFragmentManager()
        setUpFab()

        if (savedInstanceState == null) {
            startShopFragments()
        }
    }

    companion object{
        const val FRAGMENT = "FRAGMENT"
        val FRAGMENTS_WITHOUT_BACK_BUTTON = listOf(CategoryListFragment.TAG, ProfileFragment.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    private fun initObjects() {
        toolbar = mainBinding.mainToolbar.myToolbar
        userPreferences = UserPreferences(this)
    }

    private fun setUpFragmentManager() {
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                toolbar.setNavigationOnClickListener{ onBackPressed() }
            } else {
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    private fun setUpToolBar() {
        setSupportActionBar(toolbar)
    }

    private fun setUpFab() {
        mainBinding.fab.setOnClickListener {
            userPreferences.logOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        if (userPreferences.isLoggedIn()) {
            mainBinding.fab.hide()
        }
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
                    startProfileFragment()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun startProfileFragment() {
        loadFragment(ProfileFragment.newInstance(), ProfileFragment.TAG)
    }

    private fun startShopFragments() {
         loadFragment(CategoryListFragment.newInstance(), CategoryListFragment.TAG)
    }

    private fun startHelpFragment() {
        // TODO add help activity (if necessary)
        // loadFragment()
    }

    private fun loadFragment(fragment: Fragment, fragment_name : String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment, fragment_name)

            if (!FRAGMENTS_WITHOUT_BACK_BUTTON.contains(fragment_name))
                addToBackStack(fragment_name)
            else
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            commit()
        }
    }
}
