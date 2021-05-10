package com.dudnyk.projectwithmaterialdesign.UserAccountFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dudnyk.projectwithmaterialdesign.R
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentOrderListBinding
import com.google.android.material.appbar.MaterialToolbar

class OrderListFragment : Fragment() {
    private lateinit var orderBinding: FragmentOrderListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        orderBinding = FragmentOrderListBinding.inflate(inflater, container, false)
        initView()
        return orderBinding.root
    }

    private fun initView() {

    }

    companion object {
        fun newInstance() =
            OrderListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}