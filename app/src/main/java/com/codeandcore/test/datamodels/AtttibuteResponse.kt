package com.codeandcore.test.datamodels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AtttibuteResponse(
    val `data`: ArrayList<Data>?=null,
    val message: String?=null,
    val status: Int?=null,
    val success: Boolean?=null
):Parcelable
@Parcelize
data class AtttibuteResponseList(var data : ArrayList<AtttibuteResponse>):Parcelable