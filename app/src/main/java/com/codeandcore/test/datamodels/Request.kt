package com.codeandcore.test.datamodels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Request(
    var option_id: String?=null,
    var  attribute_id: String?=null,
    var product_id:String?=null
):Parcelable