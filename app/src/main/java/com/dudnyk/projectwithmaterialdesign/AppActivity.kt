package com.dudnyk.projectwithmaterialdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.MaterialToolbar

class AppActivity : AppCompatActivity() {
    private lateinit var topAppBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        setUpToolBar(R.string.about_app_title)
    }

    private fun setUpToolBar(title: Int) {
        topAppBar = findViewById(R.id.my_toolbar)
        setSupportActionBar(topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        topAppBar.setTitle(title)
        topAppBar.setNavigationOnClickListener {
            finish()
        }
    }
}