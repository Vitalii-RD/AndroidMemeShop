package com.dudnyk.projectwithmaterialdesign

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dudnyk.projectwithmaterialdesign.Data.User
import com.dudnyk.projectwithmaterialdesign.Helpers.InputValidation
import com.dudnyk.projectwithmaterialdesign.Preferences.UserPreferences
import com.dudnyk.projectwithmaterialdesign.SQL.DatabaseHelper
import com.dudnyk.projectwithmaterialdesign.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        setUpToolBar(R.string.register_title)
        initListeners()
        initObjects()
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
        inputValidation = InputValidation(this)
        databaseHelper = DatabaseHelper(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonRegister -> registerNewUser()
            R.id.loginLink -> login()
        }
    }

    private fun login() {
        finish()
    }

    private fun registerNewUser() {
        val user = postDataToSQLite()
        user?.let {
            UserPreferences(this).setCurrentUser(it)
            Snackbar.make(registerBinding.registerNestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()
            emptyInputEditText()
            showProfile()
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
        ).also {
            val userId = databaseHelper.addUser(it)
            it.id = userId
        }

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

    private fun showProfile() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.FRAGMENT, ProfileFragment.TAG)
        startActivity(intent)
    }
}