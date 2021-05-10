package com.dudnyk.projectwithmaterialdesign.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dudnyk.projectwithmaterialdesign.Data.CartItem
import com.dudnyk.projectwithmaterialdesign.databinding.CartItemBinding

class CartAdapter: BaseRecyclerViewAdapter<CartItem>() {
    private lateinit var cartItemBinding: CartItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        cartItemBinding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(cartItemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as? ProductHolder
        val item = getItem(position)
        item?.apply {
            myHolder?.setUpView(this)
        }
    }

    inner class ProductHolder(var binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private var cartItem: CartItem? = null

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener?.onItemClick(adapterPosition, v)
        }

        fun setUpView(cartItem: CartItem) {
            this.cartItem = cartItem
            binding.productName.text = cartItem.product.name
            binding.productImg.setImageResource(cartItem.product.resId)
            binding.productPrice.text = cartItem.product.getFormattedPrice()
            binding.productSelectedAmount.text = cartItem.getFormattedQuantity()
        }
    }
}