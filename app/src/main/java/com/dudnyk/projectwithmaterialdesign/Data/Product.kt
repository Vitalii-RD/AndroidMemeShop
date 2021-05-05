package com.dudnyk.projectwithmaterialdesign.Data

import android.os.Parcel
import android.os.Parcelable

data class Product(var name: String, var price: Long, var resId: Int,
                   var categories: List<Category> = listOf(), var description: String = "") : Parcelable{

    constructor(parcel: Parcel) : this(parcel.readString().toString(), parcel.readLong(), parcel.readInt()) {
        val list = mutableListOf<Category>()
        parcel.readList(list, ClassLoader.getSystemClassLoader())
        categories = list
        description = parcel.readString().toString()
    }

    fun getFormattedPrice(): String {
        return "${price}$"
    }

    override fun toString(): String = name

    fun hasCategory(category: Category): Boolean {
        return categories.any { it.isSameCategory(category) }
    }

    fun hasCategory(category: String): Boolean {
        return categories.any { it.isSameCategory(category) }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeLong(price)
        parcel.writeInt(resId)
        parcel.writeList(categories)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}