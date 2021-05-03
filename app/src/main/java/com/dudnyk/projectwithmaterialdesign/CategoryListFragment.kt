package com.dudnyk.projectwithmaterialdesign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dudnyk.projectwithmaterialdesign.adapters.CategoryAdapter
import com.dudnyk.projectwithmaterialdesign.adapters.OnItemClickListener
import com.dudnyk.projectwithmaterialdesign.data.Category
import com.google.android.material.appbar.MaterialToolbar


class CategoryListFragment : Fragment() {
    protected lateinit var rootView: View
    lateinit var toolbar: MaterialToolbar
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateComponent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_categories, container, false)
        initView()
        setUpToolBar(R.string.categories_title)
        return rootView
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
        recyclerView = rootView.findViewById(R.id.category_list)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private fun setUpAdapter() {
        adapter.setOnItemClickListener(onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                val category = adapter.getItem(position)
                fragmentManager?.beginTransaction()?.apply {
                    replace(R.id.shopping_fragments, ProductListFragment.newInstance(category), ProductListFragment.TAG)
                    addToBackStack(ProductListFragment.TAG)
                    commit()
                }
            }
        })
    }

    private fun setUpDummyData() {
        adapter.addItems(listOf(
            Category("Animals", null),
            Category("Country", null),
            Category("Food", null),
            Category("Movement", null),
            Category("Music", null),
            Category("Religion", null),
            Category("Sport", null),
            Category("Technology", null),
            Category("Auction", null),
            Category("Campaign", null),
            Category("News", null)
        ))
    }

    private fun setUpToolBar(title: Int) {
        toolbar = activity?.findViewById(R.id.my_toolbar)!!
        toolbar.setTitle(title)
    }
}