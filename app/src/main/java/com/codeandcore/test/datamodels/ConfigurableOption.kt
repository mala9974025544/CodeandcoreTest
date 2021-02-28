package com.codeandcore.test.datamodels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConfigurableOption(
    val attribute_code: String?=null,
    val attribute_id: String?=null,
    val attribute_text: String?=null,
    val attributes: List<Attribute>?=null,
    var is_Selcte:Boolean=false,
    val type: String?=null
):Parcelable