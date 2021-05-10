package com.dudnyk.projectwithmaterialdesign.UserAccountFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dudnyk.projectwithmaterialdesign.Adapters.CartAdapter
import com.dudnyk.projectwithmaterialdesign.Adapters.OnItemClickListener
import com.dudnyk.projectwithmaterialdesign.Preferences.UserPreferences
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var cartBinding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initObjects(inflater, container)
        initView()
        return cartBinding.root
    }

    companion object {
        fun newInstance() =
            CartFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    private fun initObjects(inflater: LayoutInflater, container: ViewGroup?) {
        cartBinding = FragmentCartBinding.inflate(inflater, container, false)
        userPreferences = UserPreferences(cartBinding.root.context)
        setUpAdapter()
    }

    private fun initView() {
        initializeRecyclerView()
        initializeVisibility()
    }

    private fun initializeVisibility() {
        if (cartAdapter.itemCount ==  0) {
            cartBinding.cartProductList.visibility = View.GONE
            cartBinding.emptyView.visibility = View.VISIBLE
        }
        else {
            cartBinding.cartProductList.visibility = View.VISIBLE
            cartBinding.emptyView.visibility = View.GONE
        }
    }

    private fun setUpAdapter() {
        val shoppingCart = userPreferences.getCurrentShoppingCart()
        cartAdapter = CartAdapter()
        cartAdapter.addItems(shoppingCart.products)

        cartAdapter.setOnItemClickListener(onItemClickListener = object: OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {}
        })
    }

    private fun initializeRecyclerView() {
        cartBinding.cartProductList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = cartAdapter
        }
    }
}