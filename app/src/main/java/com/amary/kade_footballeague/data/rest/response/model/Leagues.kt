package com.amary.kade_footballeague.data.rest.response.model

import com.google.gson.annotations.SerializedName

data class Leagues (
    @SerializedName("idLeague") val idLeague : String,
    @SerializedName("strLeague") val strLeague : String,
    @SerializedName("strSport") val strSport : String?
)