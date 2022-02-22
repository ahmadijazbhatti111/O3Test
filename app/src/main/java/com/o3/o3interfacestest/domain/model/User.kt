package com.o3.o3interfacestest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val age: String="",
    val count: Int=0,
    val name: String="",
): Parcelable {
    @IgnoredOnParcel
    var userImageRef:String=""
}