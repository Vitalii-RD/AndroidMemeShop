package com.dudnyk.projectwithmaterialdesign

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dudnyk.projectwithmaterialdesign.adapters.OnItemClickListener
import com.dudnyk.projectwithmaterialdesign.adapters.ProductAdapter
import com.dudnyk.projectwithmaterialdesign.data.Category
import com.dudnyk.projectwithmaterialdesign.data.Product
import com.google.android.material.appbar.MaterialToolbar

class ProductListFragment : Fragment() {
    private lateinit var toolbar: MaterialToolbar
    protected lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateComponent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_product_list, container, false)
        initView()
        return rootView
    }

    companion object {
        var TAG = ProductListFragment::class.java.simpleName
        const val CATEGORY: String = "CATEGORY"

        fun newInstance(category: Category?): ProductListFragment {
            val fragment = ProductListFragment()
            val args = Bundle()
            args.putString(CATEGORY, category?.title)
            fragment.arguments = args
            return fragment
        }
    }

    private fun onCreateComponent() {
        adapter = ProductAdapter()
        setUpAdapter()
        loadData(arguments?.getString(CATEGORY))
    }

    private fun initView() {
        initializeRecyclerView()
        setUpToolBar(R.string.products_title, arguments?.getString(CATEGORY))
    }

    private fun setUpToolBar(title: Int, category: String?) {
        toolbar = activity?.findViewById(R.id.my_toolbar)!!
        toolbar.setTitle("${resources.getString(title)}: ${category ?: R.string.all}")
    }

    private fun initializeRecyclerView() {
        recyclerView = rootView.findViewById(R.id.product_list)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private fun setUpAdapter() {
        adapter.setOnItemClickListener(onItemClickListener = object: OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                var product = adapter.getItem(position)
                Log.i("here", "here")
                fragmentManager?.beginTransaction()?.apply {
                    replace(R.id.shopping_fragments, ProductDetailFragment.newInstance("", ""), ProductDetailFragment.TAG)
                    addToBackStack(ProductDetailFragment.TAG)
                    commit()
                }
            }
        })
    }

    private fun loadData(string: String?) {
        var data = getDummyData()
        string?.let { category ->
            data = data.filter { it.hasCategory(category) }
        }
        adapter.addItems(data)
    }

    private fun getDummyData(): List<Product> {
        return listOf(
            Product("Trade Deal meme", 10, R.drawable.trade, listOf(Category("Auction", null))),
            Product("Cheating meme", 30, R.drawable.cheating, listOf(Category("News", null))),
            Product("Doggo meme", 50, R.drawable.tiktok, listOf(Category("Animals", null))),
            Product("Penguin meme", 50, R.drawable.penguin, listOf(Category("Animals", null)))
        )
    }
}