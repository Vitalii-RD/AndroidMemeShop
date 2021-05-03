package com.dudnyk.projectwithmaterialdesign.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dudnyk.projectwithmaterialdesign.R
import com.dudnyk.projectwithmaterialdesign.data.Category
import com.dudnyk.projectwithmaterialdesign.inflate

class CategoryAdapter: BaseRecyclerViewAdapter<Category>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflatedView = parent.inflate(R.layout.categoty_item, false)
        return CategoryHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as? CategoryHolder
        val category = getItem(position)
        category?.apply {
            myHolder?.setUpView(this)
        }
    }

    inner class CategoryHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var category: Category? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener?.onItemClick(adapterPosition, v)
        }

        fun setUpView(category: Category) {
            this.category = category
            view.findViewById<TextView>(R.id.category_name).text = category.title
        }
    }
}