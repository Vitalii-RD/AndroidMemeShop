package com.dudnyk.projectwithmaterialdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dudnyk.projectwithmaterialdesign.adapters.CategoryAdapter
import com.dudnyk.projectwithmaterialdesign.adapters.ProductAdapter
import com.dudnyk.projectwithmaterialdesign.data.Category
import com.dudnyk.projectwithmaterialdesign.data.Product
import com.google.android.material.appbar.MaterialToolbar

class ProductsActivity : AppCompatActivity() {
    private lateinit var topAppBar: MaterialToolbar

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        setUpToolBar(R.string.products_title)
        setUpListView()
    }

    private fun setUpListView() {
        linearLayoutManager = LinearLayoutManager(this)
        var recyclerView: RecyclerView = findViewById(R.id.product_list)
        recyclerView.layoutManager = linearLayoutManager

        val values = listOf(
            Product("Trade Deal meme", 10, R.drawable.trade, Category("news")),
            Product("Cheating meme", 30, R.drawable.cheating, Category("news")),
            Product("Doggo meme", 50, R.drawable.tiktok, Category("animals")),
            Product("Penguin meme", 50, R.drawable.penguin, Category("animals"))
        )

        adapter = ProductAdapter(values)
        recyclerView.adapter = adapter
    }

    private fun setUpToolBar(title: Int) {
        topAppBar = findViewById(R.id.my_toolbar)
        setSupportActionBar(topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        topAppBar.setTitle(title)
        topAppBar.setNavigationOnClickListener {
            finish()
        }
    }
}