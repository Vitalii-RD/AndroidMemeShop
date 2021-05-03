package com.dudnyk.projectwithmaterialdesign

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dudnyk.projectwithmaterialdesign.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpToolBar(R.string.login_title)
    }

    private fun setUpToolBar(title: Int) {
        val toolbar = loginBinding.loginToolbar.myToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setTitle(title)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
