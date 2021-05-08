package com.dudnyk.projectwithmaterialdesign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dudnyk.projectwithmaterialdesign.Adapters.CategoryAdapter
import com.dudnyk.projectwithmaterialdesign.Adapters.OnItemClickListener
import com.dudnyk.projectwithmaterialdesign.Data.Category
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
        const val TAG = "FRAGMENT_CATEGORY"

        fun newInstance(): CategoryListFragment {
            return CategoryListFragment()
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
            Category("News", null),
            Category("Anime", null),
            Category("Book", null),
            Category("Cartoon", null),
            Category("Comic Book", null),
            Category("Fetish", null),
            Category("Manga", null),
            Category("Podcast", null),
            Category("TV Show", null),
            Category("Video Game", null),
            Category("Web Series", null),
            Category("Webcomic", null)
        ))
    }

    private fun setUpToolBar(title: Int) {
        toolbar = activity?.findViewById(R.id.my_toolbar)!!
        toolbar.setTitle(title)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }
}