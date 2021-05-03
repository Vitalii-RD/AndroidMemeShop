package com.dudnyk.projectwithmaterialdesign.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dudnyk.projectwithmaterialdesign.R
import com.dudnyk.projectwithmaterialdesign.data.Product
import com.dudnyk.projectwithmaterialdesign.inflate

class ProductAdapter: BaseRecyclerViewAdapter<Product>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflatedView = parent.inflate(R.layout.product_item, false)
        return ProductHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as? ProductHolder
        val product = getItem(position)
        product?.apply {
            myHolder?.setUpView(this)
        }
    }

    inner class ProductHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var product: Product? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener?.onItemClick(adapterPosition, v)
        }

        fun setUpView(product: Product) {
            this.product = product
            view.findViewById<TextView>(R.id.product_name).text = product.name
            view.findViewById<ImageView>(R.id.product_img).setImageDrawable(ContextCompat.getDrawable(view.context, product.link))
            view.findViewById<TextView>(R.id.product_price).text = "${product.price}$"
        }
    }
}