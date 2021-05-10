package com.dudnyk.projectwithmaterialdesign.Data

import android.os.Parcel
import android.os.Parcelable
import com.dudnyk.projectwithmaterialdesign.R

data class User(var id: Int = UNREGISTERED_USER_ID, val name: String, val email: String, val password: String, var resId: Int = R.drawable.known_user): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString() ?: "", parcel.readString() ?: "", parcel.readString() ?: "", parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeInt(resId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        const val UNREGISTERED_USER_ID = -1

        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}