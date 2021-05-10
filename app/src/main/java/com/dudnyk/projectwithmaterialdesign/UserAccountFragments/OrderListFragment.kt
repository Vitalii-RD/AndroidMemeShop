package com.dudnyk.projectwithmaterialdesign.UserAccountFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dudnyk.projectwithmaterialdesign.Adapters.OnItemClickListener
import com.dudnyk.projectwithmaterialdesign.Adapters.OrderAdapter
import com.dudnyk.projectwithmaterialdesign.Preferences.UserPreferences
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentOrderListBinding

class OrderListFragment : Fragment() {
    private lateinit var orderBinding: FragmentOrderListBinding
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var userPreferences: UserPreferences
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initObjects(inflater, container)
        initView()
        return orderBinding.root
    }
    
    companion object {
        fun newInstance() =
            OrderListFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    private fun initObjects(inflater: LayoutInflater, container: ViewGroup?) {
        orderBinding = FragmentOrderListBinding.inflate(inflater, container, false)
        userPreferences = UserPreferences(orderBinding.root.context)
        setUpAdapter()
    }

    private fun initView() {
        initializeRecyclerView()
        initializeVisibility()
    }

    private fun initializeVisibility() {
        if (orderAdapter.itemCount ==  0) {
            orderBinding.orderList.visibility = View.GONE
            orderBinding.noOrders.visibility = View.VISIBLE
        }
        else {
            orderBinding.orderList.visibility = View.VISIBLE
            orderBinding.noOrders.visibility = View.GONE
        }
    }

    private fun setUpAdapter() {
        val orders = userPreferences.getOrders()
        orderAdapter = OrderAdapter()
        orderAdapter.addItems(orders)

        orderAdapter.setOnItemClickListener(onItemClickListener = object: OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {}
        })
    }

    private fun initializeRecyclerView() {
        orderBinding.orderList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = orderAdapter
        }
    }
}