package com.codeandcore.test.datamodels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConfigurableOption(
    val attribute_code: String,
    val attribute_id: String,
    val attribute_text: String,
    val attributes: List<Attribute>,
    val type: String
):Parcelable