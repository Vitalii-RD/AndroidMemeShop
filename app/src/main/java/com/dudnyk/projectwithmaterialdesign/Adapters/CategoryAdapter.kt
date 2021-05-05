package com.dudnyk.projectwithmaterialdesign.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dudnyk.projectwithmaterialdesign.Data.Category
import com.dudnyk.projectwithmaterialdesign.databinding.CategotyItemBinding

class CategoryAdapter: BaseRecyclerViewAdapter<Category>() {
    private lateinit var categoryBinding: CategotyItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        categoryBinding =  CategotyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(categoryBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as? CategoryHolder
        val category = getItem(position)
        category?.apply {
            myHolder?.setUpView(this)
        }
    }

    inner class CategoryHolder(var binding: CategotyItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private var category: Category? = null

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener?.onItemClick(adapterPosition, v)
        }

        fun setUpView(category: Category) {
            this.category = category
            binding.categoryName.text = category.title
        }
    }
}