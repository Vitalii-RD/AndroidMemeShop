package com.dudnyk.projectwithmaterialdesign.Data

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class ShoppingCart(var id: Int = UNREGISTERED_CARD_ID, var userId: Int = User.UNREGISTERED_USER_ID, var orderDate: Calendar? = null): Parcelable {
    var products: MutableList<CartItem> = mutableListOf()

    constructor(parcel: Parcel) : this(parcel.readInt()) {
        val list = mutableListOf<CartItem>()
        parcel.readList(products, ClassLoader.getSystemClassLoader())
        products = list
    }

    constructor(userId: Int): this(getUniqueId(), userId){ }
    fun addProduct(product: Product) {
        val item = getItem(product.name)
        if (item != null)
            item.quantity++
        else
            products.add(CartItem(product, 1))
    }
    fun removeProduct(product: Product) {
        val item = getItem(product.name)
        if (item != null && item.quantity < 1)
            item.quantity--
        else
            products.remove(item)
    }

    fun getTotalPrice(): Long {
        return products.map { it.quantity * it.product.price }.sum()
    }

    fun getFormattedTotalPrice(): String {
        return "${getTotalPrice()}$"
    }

    fun size(): Int {
        return products.count()
    }

    fun getCartName(): String {
        return "Order #${id}"
    }

    private fun getItem(name: String) : CartItem? {
        return products.find { it.product.name == name }
    }

    companion object CREATOR : Parcelable.Creator<ShoppingCart> {
        const val UNREGISTERED_CARD_ID = -1
        private var ids = 0

        private fun getUniqueId(): Int {
            return ids++
        }

        override fun createFromParcel(parcel: Parcel): ShoppingCart {
            return ShoppingCart(parcel)
        }

        override fun newArray(size: Int): Array<ShoppingCart?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeList(products)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun isEmpty(): Boolean {
        return size() == 0
    }
}
