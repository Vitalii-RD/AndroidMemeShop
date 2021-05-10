package com.dudnyk.projectwithmaterialdesign.UserAccountFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dudnyk.projectwithmaterialdesign.R
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentSecuritySettingsBinding

class SecuritySettingsFragment : Fragment() {
    private lateinit var binding: FragmentSecuritySettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSecuritySettingsBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {

    }

    companion object {
        fun newInstance() =
            SecuritySettingsFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}