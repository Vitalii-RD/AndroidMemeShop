package com.dudnyk.projectwithmaterialdesign

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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

        supportFragmentManager.beginTransaction()
            .replace(R.id.shopping_fragments, CategoryListFragment.newInstance(), CategoryListFragment.TAG)
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
}