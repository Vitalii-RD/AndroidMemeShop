package com.dudnyk.projectwithmaterialdesign.Data

import android.os.Parcel
import android.os.Parcelable

data class Category(var title: String, val resId: Int?) : Parcelable{
    constructor(parcel: Parcel) : this(parcel.readString().toString(), parcel.readValue(Int::class.java.classLoader) as? Int)

    override fun toString(): String = title

    fun isSameCategory(category: Category): Boolean {
        return isSameCategory(category.title)
    }

    fun isSameCategory(category: String): Boolean {
        return category.toLowerCase() == title.toLowerCase()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeValue(resId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}