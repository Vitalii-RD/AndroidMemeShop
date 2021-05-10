package com.dudnyk.projectwithmaterialdesign.UserAccountFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dudnyk.projectwithmaterialdesign.R
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentPaymentDeliveryBinding
import com.google.android.material.appbar.MaterialToolbar

class PaymentDeliveryFragment : Fragment() {
    private lateinit var binding: FragmentPaymentDeliveryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPaymentDeliveryBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {

    }

    companion object {
        fun newInstance() =
            PaymentDeliveryFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}