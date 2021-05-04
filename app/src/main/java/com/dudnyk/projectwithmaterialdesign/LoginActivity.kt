package com.dudnyk.projectwithmaterialdesign

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dudnyk.projectwithmaterialdesign.Helpers.InputValidation
import com.dudnyk.projectwithmaterialdesign.SQL.DatabaseHelper
import com.dudnyk.projectwithmaterialdesign.data.User
import com.dudnyk.projectwithmaterialdesign.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var sp : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        setUpToolBar(R.string.login_title)
        initListeners()
        initObjects()
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

    private fun initListeners() {
        loginBinding.appCompatButtonLogin.setOnClickListener(this)
        loginBinding.textViewLinkRegister.setOnClickListener(this)
    }

    private fun initObjects() {
        databaseHelper = DatabaseHelper(this)
        inputValidation = InputValidation(this)
        sp = getSharedPreferences(RegisterActivity.LOGIN, Context.MODE_PRIVATE)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.appCompatButtonLogin -> {
                if (verifyFromSQLite()) {
                    getUser()?.let {
                        emptyInputEditText()
                        showProfile(it)
                    }
                }
            }

            R.id.textViewLinkRegister -> startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }
    }

    private fun verifyFromSQLite(): Boolean {
        if (!inputValidation.isInputEditTextFilled(loginBinding.inputEmail, loginBinding.inputLayoutEmail, getString(R.string.error_message_email)))
            return false

        if (!inputValidation.isInputEditTextEmail(loginBinding.inputEmail, loginBinding.inputLayoutEmail, getString(R.string.error_message_email)))
            return false

        if (!inputValidation.isInputEditTextFilled(loginBinding.inputPassword, loginBinding.inputLayoutPassword, getString(R.string.error_message_email)))
            return false

        if (!databaseHelper.checkUser(loginBinding.inputEmail.text.toString().trim { it <= ' ' }, loginBinding.inputPassword.text.toString().trim { it <= ' ' })) {
            Snackbar.make(loginBinding.loginNestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun getUser(): User? {
        return databaseHelper.getUser(
            loginBinding.inputEmail.text.toString().trim { it <= ' ' },
            loginBinding.inputPassword.text.toString().trim { it <= ' ' }
        )
    }

    private fun showProfile(user: User) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.FRAGMENT, MainActivity.FRAGMENT_PROFILE)

        sp.edit().putInt(RegisterActivity.USER_ID, user.id).apply()
        sp.edit().putString(RegisterActivity.USER_NAME, user.name).apply()

        startActivity(intent)
    }

    private fun emptyInputEditText() {
        loginBinding.inputEmail.text = null
        loginBinding.inputPassword.text = null
    }

}
