package com.dudnyk.projectwithmaterialdesign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dudnyk.projectwithmaterialdesign.Adapters.OnItemClickListener
import com.dudnyk.projectwithmaterialdesign.Adapters.ProductAdapter
import com.dudnyk.projectwithmaterialdesign.Data.Category
import com.dudnyk.projectwithmaterialdesign.Data.Product
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentProductListBinding
import com.google.android.material.appbar.MaterialToolbar

class ProductListFragment : Fragment() {
    private lateinit var toolbar: MaterialToolbar
    lateinit var listBinding: FragmentProductListBinding
    lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObjects()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        listBinding = FragmentProductListBinding.inflate(inflater, container, false)
        initView()
        return listBinding.root
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

    private fun initObjects() {
        productAdapter = ProductAdapter()
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
        listBinding.productList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = productAdapter
        }
    }

    private fun setUpAdapter() {
        productAdapter.setOnItemClickListener(onItemClickListener = object: OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                val product = productAdapter.getItem(position)!!
                val container = if (context?.resources?.getBoolean(R.bool.two_pane) == true) R.id.product_list_detail else R.id.fragment_container

                fragmentManager?.beginTransaction()?.apply {
                    replace(container, ProductDetailFragment.newInstance(product), ProductDetailFragment.TAG)
                    addToBackStack(ProductDetailFragment.TAG)
                    commit()
                }
            }
        })
    }

    private fun loadData(categoryName: String?) {
        var data = getDummyData()
        categoryName?.let { category ->
            data = data.filter { it.hasCategory(category) }
        }
        productAdapter.addItems(data)
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