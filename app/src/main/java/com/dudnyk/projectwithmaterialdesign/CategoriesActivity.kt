package com.dudnyk.projectwithmaterialdesign


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dudnyk.projectwithmaterialdesign.adapters.CategoryAdapter
import com.dudnyk.projectwithmaterialdesign.data.Category
import com.google.android.material.appbar.MaterialToolbar


class CategoriesActivity : AppCompatActivity() {
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectWithMaterialDesign)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        setUpToolBar(R.string.categories_title)
        setUpListView()
    }

    fun setUpListView() {
        linearLayoutManager = LinearLayoutManager(this)
        var recyclerView: RecyclerView = findViewById(R.id.category_list)
        recyclerView.layoutManager = linearLayoutManager

        val values = arrayOf(
            "Art", "Country", "Food", "Movement", "Music",
            "Religion", "Sport", "Technology", "Auction",
            "Award Ceremony", "Campaign", "Competition", "Controversy",
            "Convention", "Crime", "Disaster", "Election", "Flash Mob"
        )

        adapter = CategoryAdapter(values.map { Category(it) })
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