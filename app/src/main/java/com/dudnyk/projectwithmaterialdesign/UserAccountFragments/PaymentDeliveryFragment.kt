package com.dudnyk.projectwithmaterialdesign.UserAccountFragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dudnyk.projectwithmaterialdesign.Preferences.UserPreferences
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentPaymentDeliveryBinding


class PaymentDeliveryFragment : Fragment() {
    private lateinit var binding: FragmentPaymentDeliveryBinding
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPaymentDeliveryBinding.inflate(inflater, container, false)
        initObjects()
        initView()
        return binding.root
    }

    private fun initObjects() {
        userPreferences = UserPreferences(binding.root.context)
    }

    private fun initView() {
        setUpAdapter()
        setUpMakeOrderButton()
        binding.newOrderTotalPrice.text = userPreferences.getCurrentShoppingCart().getFormattedTotalPrice()
    }

    private fun reset() {
        binding.paymentDropdown.setSelection(0)
        binding.inputAddress.text = null
        binding.newOrderTotalPrice.text = "0$"
        setUpButtonClickability()
    }

    private fun setUpMakeOrderButton() {
        binding.makeOrderButton.setOnClickListener {
            userPreferences.makeOrder()

            Toast
                .makeText(binding.root.context, "Order successfully made", Toast.LENGTH_LONG)
                .show()

            reset()
        }
        setUpButtonClickability()
    }

    private fun setUpButtonClickability() {
        if (userPreferences.getCurrentShoppingCart().isEmpty()) {
            binding.makeOrderButton.apply {
                isEnabled = false
                isClickable = false
            }
        } else {
            binding.makeOrderButton.apply {
                isEnabled = true
                isClickable = true
            }
        }
    }

    private fun setUpAdapter() {
        val paymentMethods = arrayListOf("Pay by cash", "Pay by card", "I do not want to pay")
        val adapter = ArrayAdapter(
            binding.root.context,
            R.layout.simple_spinner_dropdown_item,
            paymentMethods
        )
        binding.paymentDropdown.adapter = adapter
    }

    companion object {
        fun newInstance() =
            PaymentDeliveryFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}