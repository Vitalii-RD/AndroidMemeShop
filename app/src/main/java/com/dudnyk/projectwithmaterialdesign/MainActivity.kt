package com.dudnyk.projectwithmaterialdesign

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar


class MainActivity : AppCompatActivity() {
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var fab : View

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpToolBar()
        setUpFab()
    }

    private fun setUpToolBar() {
        topAppBar = findViewById(R.id.my_toolbar)
        setSupportActionBar(topAppBar)
    }

    private fun setUpFab() {
        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
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

    fun handleCategory(view: View) {
        val intent = Intent(this, CategoriesActivity::class.java)
        startActivity(intent)
    }

    fun handleProducts(view: View) {
        val intent = Intent(this, ProductsActivity::class.java)
        startActivity(intent)
    }
}