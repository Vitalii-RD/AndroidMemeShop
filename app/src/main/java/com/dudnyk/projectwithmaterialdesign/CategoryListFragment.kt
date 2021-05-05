package com.dudnyk.projectwithmaterialdesign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dudnyk.projectwithmaterialdesign.adapters.CategoryAdapter
import com.dudnyk.projectwithmaterialdesign.adapters.OnItemClickListener
import com.dudnyk.projectwithmaterialdesign.data.Category
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentCategoriesBinding
import com.google.android.material.appbar.MaterialToolbar


class CategoryListFragment : Fragment() {
    private lateinit var categoryBinding: FragmentCategoriesBinding
    private lateinit var toolbar: MaterialToolbar
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateComponent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        categoryBinding = FragmentCategoriesBinding.inflate(inflater, container, false)
        initView()
        setUpToolBar(R.string.categories_title)
        return categoryBinding.root
    }

    companion object {
        var TAG = CategoryListFragment::class.java.simpleName
        const val ARG_POSITION: String = "positioin"

        fun newInstance(): CategoryListFragment {
            val fragment = CategoryListFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, 1)
            fragment.arguments = args
            return fragment
        }
    }

    private fun onCreateComponent() {
        adapter = CategoryAdapter()
        setUpAdapter()
        setUpDummyData()
    }


    private fun initView(){
        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        categoryBinding.categoryList.layoutManager = LinearLayoutManager(activity)
        categoryBinding.categoryList.adapter = adapter
    }

    private fun setUpAdapter() {
        adapter.setOnItemClickListener(onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                val category = adapter.getItem(position)
                fragmentManager?.beginTransaction()?.apply {
                    replace(R.id.fragment_container, ProductListFragment.newInstance(category), ProductListFragment.TAG)
                    addToBackStack(ProductListFragment.TAG)
                    commit()
                }
            }
        })
    }

    private fun setUpDummyData() {
        adapter.addItems(listOf(
            Category("White Vinyl Stickers & Labels", null),
            Category("Sticker and Label Sheets", null),
            Category("Clear Vinyl Stickers & Labels", null),
            Category("Premium Kids’ Labels", null),
            Category("Permanent & Stick-On Clothing Tag Labels", null)
        ))
    }

    private fun setUpToolBar(title: Int) {
        toolbar = activity?.findViewById(R.id.my_toolbar)!!
        toolbar.setTitle(title)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }
}