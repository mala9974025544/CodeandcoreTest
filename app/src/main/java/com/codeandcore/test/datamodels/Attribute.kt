package com.codeandcore.test.datamodels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attribute(
    val option_id: Int,
    val value: String
):Parcelable