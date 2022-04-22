package com.mindbody.assessment.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryModel(
    @SerializedName("ID")
    val id: Int = 0,
    @SerializedName("Name")
    val name: String = "",
    @SerializedName("Code")
    val code: String = "",
    @SerializedName("PhoneCode")
    val phoneCode: String?
) : Parcelable {

    @IgnoredOnParcel
    val phoneCodeDisplay: String
        get() {
            return if (phoneCode != null) "(+$phoneCode)" else "(N/A)"
        }

    val flagUrl
        get() = "https://raw.githubusercontent.com/hampusborgos/country-flags/main/png250px/${code.lowercase()}.png"
}