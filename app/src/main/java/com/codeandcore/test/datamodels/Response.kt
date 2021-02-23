package com.codeandcore.test.datamodels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Response(
        var data: Data?=null,
        var message: String?=null,
        var status: Int?=null,
        var success: Boolean?=null
):Parcelable