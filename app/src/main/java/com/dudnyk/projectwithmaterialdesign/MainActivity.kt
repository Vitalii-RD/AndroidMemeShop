package com.dudnyk.projectwithmaterialdesign

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.dudnyk.projectwithmaterialdesign.Preferences.UserPreferences
import com.dudnyk.projectwithmaterialdesign.databinding.ActivityMainBinding
import com.dudnyk.projectwithmaterialdesign.databinding.NavHeaderMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var toolbar: MaterialToolbar
    private  lateinit var userPreferences: UserPreferences
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var headerBinding: NavHeaderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initObjects()
        setUpDrawer()
        setUpDrawerView()
        setUpToolBar()
        setUpBottomNavigation()
        setUpFragmentManager()
        setUpFab()

        if (savedInstanceState == null) {
            startShopFragments()
        }
    }

    override fun onRestart() {
        super.onRestart()
        setUpDrawerView()
    }

    companion object{
        const val FRAGMENT = "FRAGMENT"
        val FRAGMENTS_WITHOUT_BACK_BUTTON = listOf(CategoryListFragment.TAG, ProfileFragment.TAG)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val result = when {
            //TODO add more links
            onOptionsItemSelected(item) -> { true }
            isInBottomMenu(item) -> {
                mainBinding.appMain.bottomNavigation.selectedItemId = item.itemId
                true
            }
            isLogOut(item) -> {
                logOut()
                true
            }
            else -> false
        }

        mainBinding.drawerLayout.closeDrawer(GravityCompat.START)
        return result
    }

    private fun isLogOut(item: MenuItem) = item.itemId == R.id.d_nav_logout

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

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

    override fun onBackPressed() {
        when {
            mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START) -> {
                mainBinding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            supportFragmentManager.backStackEntryCount > 0 -> {
                supportFragmentManager.popBackStack()
            }
            else -> super.onBackPressed()
        }
    }

    private fun initObjects() {
        headerBinding = NavHeaderMainBinding.bind(mainBinding.navView.getHeaderView(0))
        toolbar = mainBinding.appMain.mainToolbar.myToolbar
        toggle = ActionBarDrawerToggle(this, mainBinding.drawerLayout, toolbar, R.string.d_nav_open, R.string.d_nav_close)
        userPreferences = UserPreferences(this)
    }

    private fun setUpDrawer() {
        mainBinding.drawerLayout.addDrawerListener(toggle)
        mainBinding.navView.setNavigationItemSelectedListener(this)
    }

    private fun setUpDrawerView() {
        val user = userPreferences.getCurrentUser()
        headerBinding.dNavProfileImg.setImageResource(user.resId)
        headerBinding.dNavUserName.text = user.name
    }

    private fun setUpFragmentManager() {
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                showBackButton(true)
            } else {
                showBackButton(false)
                toggle.syncState()
            }
        }
    }

    private fun setUpToolBar() {
        setSupportActionBar(toolbar)
    }

    private fun setUpFab() {
        mainBinding.appMain.fab.setOnClickListener { logOut() }

        if (userPreferences.isLoggedIn())
            mainBinding.appMain.fab.hide()
    }

    private fun logOut() {
        userPreferences.logOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun setUpBottomNavigation() {
        mainBinding.appMain.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.b_nav_categories -> {
                    startShopFragments()
                    true
                }
                R.id.b_nav_help -> {
                    startHelpFragment()
                    true
                }
                R.id.b_nav_profile -> {
                    startProfileFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun isInBottomMenu(item: MenuItem): Boolean {
        return listOf(R.id.b_nav_categories, R.id.b_nav_help, R.id.b_nav_profile).contains(item.itemId)
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

    private fun showBackButton(isBack: Boolean) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(isBack)
        if (isBack)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        else
            toolbar.setNavigationOnClickListener{
                mainBinding.drawerLayout.openDrawer(GravityCompat.START)
            }
    }
}
