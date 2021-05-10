package com.dudnyk.projectwithmaterialdesign.Data

data class CartItem (val product:Product, var quantity: Int) {
    fun getFormattedQuantity(): String {
        return "$quantity items"
    }
}