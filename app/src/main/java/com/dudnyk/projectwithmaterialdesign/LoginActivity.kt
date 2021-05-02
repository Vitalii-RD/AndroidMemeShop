package com.dudnyk.projectwithmaterialdesign

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class LoginActivity : AppCompatActivity() {
    private lateinit var topAppBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpToolBar(R.string.login_title)
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
