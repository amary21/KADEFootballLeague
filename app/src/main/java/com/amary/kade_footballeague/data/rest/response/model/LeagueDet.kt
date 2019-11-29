package com.amary.kade_footballeague.data.rest.response.model

import com.google.gson.annotations.SerializedName

data class LeagueDet (
    @SerializedName("idLeague") val idLeague : String,
    @SerializedName("strSport") val strSport : String,
    @SerializedName("strLeague") val strLeague : String,
    @SerializedName("strGender") val strGender : String,
    @SerializedName("strCountry") val strCountry : String,
    @SerializedName("strWebsite") val strWebsite : String,
    @SerializedName("strFacebook") val strFacebook : String,
    @SerializedName("strTwitter") val strTwitter : String,
    @SerializedName("strYoutube") val strYoutube : String,
    @SerializedName("strDescriptionEN") val strDescriptionEN : String,
    @SerializedName("strFanart1") val strFanart1 : String?,
    @SerializedName("strFanart2") val strFanart2 : String?,
    @SerializedName("strFanart3") val strFanart3 : String?,
    @SerializedName("strFanart4") val strFanart4 : String?,
    @SerializedName("strBadge") val strBadge: String?
)