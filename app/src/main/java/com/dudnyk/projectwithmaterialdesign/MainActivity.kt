package com.dudnyk.projectwithmaterialdesign

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dudnyk.projectwithmaterialdesign.data.Category
import com.dudnyk.projectwithmaterialdesign.data.Product
import com.dudnyk.projectwithmaterialdesign.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar


class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainBinding.root)
        setUpToolBar()
        setUpFab()
        setUpShopFragments()
        setUpBottomNavigation()
    }

    private fun setUpToolBar() {
        toolbar = mainBinding.mainToolbar.myToolbar
        setSupportActionBar(toolbar)
    }

    private fun setUpFab() {
        mainBinding.fab.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpShopFragments() {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_app -> {
                val intent = Intent(this, AppActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.about_author -> {
                val intent = Intent(this, AuthorActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.login -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
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
                    toolbar.title = resources.getString(R.string.categories_title)
                    setUpShopFragments()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.b_nav_help -> {
                    toolbar.title = resources.getString(R.string.help)
                    loadFragment(ProductDetailFragment.newInstance(
                        Product("Trade Deal meme", 10, R.drawable.trade, listOf(Category("Auction", null)
                    ))))
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.b_nav_profile -> {
                    title=resources.getString(R.string.profile)
//                    loadFragment(SettingsFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(fragment::class.java.simpleName)
        transaction.commit()
    }
}
