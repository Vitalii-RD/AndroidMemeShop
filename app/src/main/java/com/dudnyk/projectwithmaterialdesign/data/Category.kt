package com.dudnyk.projectwithmaterialdesign.data

data class Category(var title: String, val resId: Int?) {
    override fun toString(): String = title

    fun isSameCategory(category: Category): Boolean {
        return isSameCategory(category.title)
    }

    fun isSameCategory(category: String): Boolean {
        return category.toLowerCase() == title.toLowerCase()
    }
}