package com.dudnyk.projectwithmaterialdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dudnyk.projectwithmaterialdesign.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {
    private lateinit var appBinding: ActivityAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        appBinding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(appBinding.root)
        setUpToolBar(R.string.about_app_title)
    }

    private fun setUpToolBar(title: Int) {
        val toolbar = appBinding.appToolbar.myToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setTitle(title)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}