package com.dudnyk.projectwithmaterialdesign

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentCategoriesBinding
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentUserAccountBinding
import com.google.android.material.appbar.MaterialToolbar

class UserAccountFragment : Fragment() {
    private lateinit var userBinding: FragmentUserAccountBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userBinding = FragmentUserAccountBinding.inflate(inflater, container, false)
        initView()
        return userBinding.root
    }

    private fun setUpToolBar(title: Int) {
        val toolbar = activity?.findViewById<MaterialToolbar>(R.id.my_toolbar)!!
        toolbar.setTitle(title)
    }

    private fun initView() {
        setUpToolBar(R.string.user_account)
    }

    companion object {
        const val TAG = "USER_ACCOUNT"

        fun newInstance() =
            UserAccountFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}