package com.dudnyk.projectwithmaterialdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dudnyk.projectwithmaterialdesign.databinding.ActivityAuthorBinding

class AuthorActivity : AppCompatActivity() {
    private lateinit var authorBinding: ActivityAuthorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        authorBinding = ActivityAuthorBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_author)
        setUpToolBar(R.string.about_author_title)
    }

    private fun setUpToolBar(title: Int) {
        val toolbar = authorBinding.authorToolbar.myToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setTitle(title)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}