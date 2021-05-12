package com.dudnyk.projectwithmaterialdesign.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dudnyk.projectwithmaterialdesign.Data.ShoppingCart
import com.dudnyk.projectwithmaterialdesign.databinding.OrderItemBinding

class OrderAdapter: BaseRecyclerViewAdapter<ShoppingCart>() {
    private lateinit var orderBinding: OrderItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        orderBinding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartHolder(orderBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as? CartHolder
        val item = getItem(position)
        item?.apply {
            myHolder?.setUpView(this)
        }
    }

    inner class CartHolder(var binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private var cart: ShoppingCart? = null

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener?.onItemClick(adapterPosition, v)
        }

        fun setUpView(cart: ShoppingCart) {
            this.cart = cart

            binding.orderName.text = cart.getCartName()
            binding.orderDate.text = cart.getFormattedOrderDate()

            val images = listOf(binding.productImg1, binding.productImg2, binding.productImg3, binding.productImg4)
            cart.products.forEachIndexed { i, item ->
                if (i < 4) {
                    images[i].setImageResource(item.product.resId)
                }
            }
            
            binding.totalPrice.text = cart.getFormattedTotalPrice()
        }
    }
}