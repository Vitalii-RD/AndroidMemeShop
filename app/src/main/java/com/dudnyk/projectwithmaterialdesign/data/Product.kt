package com.dudnyk.projectwithmaterialdesign.data

data class Product(var name: String, var price: Long, var link: Int, val categories: List<Category>) {
    override fun toString(): String = name

    fun hasCategory(category: Category): Boolean {
        return categories.any { it.isSameCategory(category) }
    }

    fun hasCategory(category: String): Boolean {
        return categories.any { it.isSameCategory(category) }
    }
}