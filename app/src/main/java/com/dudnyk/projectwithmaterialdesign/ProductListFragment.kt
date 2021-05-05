package com.dudnyk.projectwithmaterialdesign

import android.os.Bundle
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
                val product = adapter.getItem(position)!!
                val container = if (context?.resources?.getBoolean(R.bool.two_pane) == true) R.id.product_list_detail else R.id.fragment_container

                fragmentManager?.beginTransaction()?.apply {
                    replace(container, ProductDetailFragment.newInstance(product), ProductDetailFragment.TAG)
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
            Product("Clear sticker with name", 6, R.drawable.name_clear_sticker, listOf(Category("Clear Vinyl Stickers & Labels", null))),
            Product("Fox stick-on clothing label", 7, R.drawable.fox_stick_on_clothing_label, listOf(Category("Permanent & Stick-On Clothing Tag Labels", null))),
            Product("Minimalistic die cut sticker", 8, R.drawable.minimalistic_die_cut_sticker, listOf(Category("White Vinyl Stickers & Labels    ", null))),
            Product("Simple sticker pack", 10, R.drawable.sticker_pack, listOf(Category("Sticker and Label Sheets", null)))
        )
    }
}