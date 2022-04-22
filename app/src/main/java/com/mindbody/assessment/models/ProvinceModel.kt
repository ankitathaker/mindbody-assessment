package com.mindbody.assessment.models

import com.google.gson.annotations.SerializedName

data class ProvinceModel(
    @SerializedName("ID")
    val id: Int = 0,
    @SerializedName("CountryCode")
    val countryCode: String = "",
    @SerializedName("Code")
    val code: String = "",
    @SerializedName("Name")
    val name: String = ""
)