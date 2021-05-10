package com.dudnyk.projectwithmaterialdesign.UserAccountFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dudnyk.projectwithmaterialdesign.Data.User
import com.dudnyk.projectwithmaterialdesign.Helpers.InputValidation
import com.dudnyk.projectwithmaterialdesign.MainActivity
import com.dudnyk.projectwithmaterialdesign.Preferences.UserPreferences
import com.dudnyk.projectwithmaterialdesign.R
import com.dudnyk.projectwithmaterialdesign.SQL.DatabaseHelper
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentSecuritySettingsBinding
import com.google.android.material.snackbar.Snackbar

class SecuritySettingsFragment : Fragment(), View.OnClickListener {
    private lateinit var settingsBinding: FragmentSecuritySettingsBinding
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var userPreferences: UserPreferences
    private lateinit var currentUser: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initObjects(inflater, container)
        initListeners()
        initView()
        return settingsBinding.root
    }

    companion object {

        fun newInstance() =
            SecuritySettingsFragment().apply {}
    }
    private fun initObjects(inflater: LayoutInflater, container: ViewGroup?) {
        settingsBinding = FragmentSecuritySettingsBinding.inflate(inflater, container, false)
        inputValidation = InputValidation(settingsBinding.root.context)
        databaseHelper = DatabaseHelper(settingsBinding.root.context)
        userPreferences = UserPreferences(settingsBinding.root.context)
        currentUser = UserPreferences(settingsBinding.root.context).getCurrentUser()
    }

    private fun initListeners() {
        settingsBinding.save.setOnClickListener(this)
    }

    private fun initView() {
        settingsBinding.inputName.setText(currentUser.name)
        settingsBinding.inputEmail.setText(currentUser.email)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.save -> saveNewSettings()
        }
    }

    private fun saveNewSettings() {
        val user = postDataToSQLite()
        user?.let {
            userPreferences.setCurrentUser(it)
            Snackbar.make(settingsBinding.registerNestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()
            emptyInputEditText()
            (activity as MainActivity).setUpDrawerView()
        }
    }

    private fun postDataToSQLite(): User? {
        var user: User? = null

        if (!isValidUserInfo())
            return user

        val userData = getUserFromInput()

        user = User(currentUser.id, userData.name, userData.email, userData.password)
        databaseHelper.updateUser(user)

        return user
    }

    private fun getUserFromInput(): User  {
        return User(
            User.UNREGISTERED_USER_ID,
            settingsBinding.inputName.text.toString().trim(),
            settingsBinding.inputEmail.text.toString().trim(),
            settingsBinding.inputPassword.text.toString().trim()
        )
    }

    private fun emptyInputEditText() {
        settingsBinding.inputName.text = null
        settingsBinding.inputEmail.text = null
        settingsBinding.inputPassword.text = null
        settingsBinding.inputConfirmPassword.text = null
    }

    private fun isValidUserInfo(): Boolean {
        if (!inputValidation.isInputEditTextFilled(settingsBinding.inputName, settingsBinding.inputLayoutName, getString(R.string.error_message_name)))
            return false

        if (!inputValidation.isInputEditTextFilled(settingsBinding.inputEmail, settingsBinding.inputLayoutEmail, getString(R.string.error_message_email)))
            return false

        if (!inputValidation.isInputEditTextEmail(settingsBinding.inputEmail, settingsBinding.inputLayoutEmail, getString(R.string.error_message_email)))
            return false

        if (!inputValidation.isInputEditTextFilled(settingsBinding.inputPassword, settingsBinding.inputLayoutPassword, getString(R.string.error_message_password)))
            return false

        if (!inputValidation.isInputEditTextMatches(settingsBinding.inputPassword, settingsBinding.inputConfirmPassword, settingsBinding.inputLayoutConfirmPassword, getString(R.string.error_password_match)))
            return false

        return true
    }
}