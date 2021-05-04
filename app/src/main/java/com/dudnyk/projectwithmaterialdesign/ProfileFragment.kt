package com.dudnyk.projectwithmaterialdesign

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import com.dudnyk.projectwithmaterialdesign.SQL.DatabaseHelper
import com.dudnyk.projectwithmaterialdesign.data.User
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentProfileBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfileFragment : Fragment() {
    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var toolbar: MaterialToolbar
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var user: User
    private lateinit var sp: SharedPreferences
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        setUpToolBar(R.string.profile)
        initObjects()
        initView()

        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab = activity?.findViewById(R.id.fab)!!
        fab.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isLoggedIn()) {
            fab.hide()
        }
    }

    fun isLoggedIn(): Boolean {
        return sp.getInt(RegisterActivity.USER_ID, -1) != -1
    }

    companion object {
        const val USER = "USER"
        fun newInstance() = ProfileFragment()
    }


    private fun setUpToolBar(title: Int) {
        toolbar = activity?.findViewById(R.id.my_toolbar)!!
        toolbar.setTitle(title)
    }

    private fun initObjects() {
        databaseHelper = DatabaseHelper(profileBinding.root.context)
        sp = activity?.getSharedPreferences(RegisterActivity.LOGIN, Context.MODE_PRIVATE)!!
        user = databaseHelper.getUser(sp.getInt(RegisterActivity.USER_ID, -1))
                ?: User(-1, "unknown", "unknown", "unknown")
    }

    private fun initView() {
        //TODO set user image
        profileBinding.profileImg.setImageDrawable(ContextCompat.getDrawable(profileBinding.root.context, R.drawable.unknown_user))
        profileBinding.profileName.text = user.name
        profileBinding.profileEmail.text = user.email
    }

}