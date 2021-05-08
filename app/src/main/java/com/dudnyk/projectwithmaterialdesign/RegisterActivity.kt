package com.dudnyk.projectwithmaterialdesign

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dudnyk.projectwithmaterialdesign.Helpers.InputValidation
import com.dudnyk.projectwithmaterialdesign.SQL.DatabaseHelper
import com.dudnyk.projectwithmaterialdesign.Data.User
import com.dudnyk.projectwithmaterialdesign.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar


class RegisterActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        setUpToolBar(R.string.register_title)
        initListeners()
        initObjects()
    }

    companion object{
        const val LOGIN = "LOGIN"
        const val USER_ID = "USER_ID"
        const val USER_NAME = "USER_NAME"
    }

    private fun setUpToolBar(title: Int) {
        val toolbar = registerBinding.registerToolbar.myToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setTitle(title)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initListeners() {
        registerBinding.buttonRegister.setOnClickListener(this)
        registerBinding.loginLink.setOnClickListener(this)
    }

    private fun initObjects() {
        sp = getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
        inputValidation = InputValidation(this)
        databaseHelper = DatabaseHelper(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonRegister -> {
                val user = postDataToSQLite()
                user?.let { showProfile(it) }
            }
            R.id.loginLink -> finish()
        }
    }

    private fun postDataToSQLite(): User? {
        var user: User? = null

        if (!isValidUserInfo())
            return user

        user = User(
            name = registerBinding.inputName.text.toString().trim(),
            email = registerBinding.inputEmail.text.toString().trim(),
            password = registerBinding.inputPassword.text.toString().trim()
        )
        databaseHelper.addUser(user)
        Snackbar.make(registerBinding.registerNestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()
        emptyInputEditText()
        return user
    }

    private fun emptyInputEditText() {
        registerBinding.inputName.text = null
        registerBinding.inputEmail.text = null
        registerBinding.inputPassword.text = null
        registerBinding.inputConfirmPassword.text = null
    }

    private fun isValidUserInfo(): Boolean {
        if (!inputValidation.isInputEditTextFilled(registerBinding.inputName, registerBinding.inputLayoutName, getString(R.string.error_message_name)))
            return false

        if (!inputValidation.isInputEditTextFilled(registerBinding.inputEmail, registerBinding.inputLayoutEmail, getString(R.string.error_message_email)))
            return false

        if (!inputValidation.isInputEditTextEmail(registerBinding.inputEmail, registerBinding.inputLayoutEmail, getString(R.string.error_message_email)))
            return false

        if (!inputValidation.isInputEditTextFilled(registerBinding.inputPassword, registerBinding.inputLayoutPassword, getString(R.string.error_message_password)))
            return false

        if (!inputValidation.isInputEditTextMatches(registerBinding.inputPassword, registerBinding.inputConfirmPassword,
                registerBinding.inputLayoutConfirmPassword, getString(R.string.error_password_match)))
            return false

        if (databaseHelper.checkUser(registerBinding.inputEmail.text.toString().trim())) {
            Snackbar.make(registerBinding.registerNestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun showProfile(user: User) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.FRAGMENT, MainActivity.FRAGMENT_PROFILE)

        sp.edit().putInt(USER_ID, user.id).apply()
        sp.edit().putString(USER_NAME, user.name).apply()

        startActivity(intent)
    }
}