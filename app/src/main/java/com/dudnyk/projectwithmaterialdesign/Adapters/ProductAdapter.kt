package com.dudnyk.projectwithmaterialdesign.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dudnyk.projectwithmaterialdesign.Data.Product
import com.dudnyk.projectwithmaterialdesign.databinding.ProductItemBinding

class ProductAdapter: BaseRecyclerViewAdapter<Product>() {
    private lateinit var productBinding: ProductItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        productBinding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(productBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as? ProductHolder
        val product = getItem(position)
        product?.apply {
            myHolder?.setUpView(this)
        }
    }

    inner class ProductHolder(var binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private var product: Product? = null

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener?.onItemClick(adapterPosition, v)
        }

        fun setUpView(product: Product) {
            this.product = product
            binding.productName.text = product.name
            binding.productImg.setImageDrawable(ContextCompat.getDrawable(productBinding.root.context, product.resId))
            binding.productPrice.text = product.getFormattedPrice()
        }
    }
}