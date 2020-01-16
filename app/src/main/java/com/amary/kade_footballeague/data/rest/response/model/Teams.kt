package com.amary.kade_footballeague.data.rest.response.model

import com.google.gson.annotations.SerializedName

data class Teams (
    @SerializedName("idTeam") val idTeam : String,
    @SerializedName("strTeam") val strTeam : String,
    @SerializedName("strSport") val strSport : String,
    @SerializedName("strLeague") val strLeague : String,
    @SerializedName("idLeague") val idLeague : String,
    @SerializedName("strStadium") val strStadium : String,
    @SerializedName("strStadiumThumb") val strStadiumThumb : String,
    @SerializedName("strStadiumDescription") val strStadiumDescription : String,
    @SerializedName("strDescriptionEN") val strDescriptionEN : String,
    @SerializedName("strTeamBadge") val strTeamBadge : String,
    @SerializedName("strTeamJersey") val strTeamJersey : String,
    @SerializedName("strTeamFanart1") val strTeamFanart1 : String?,
    @SerializedName("strTeamFanart2") val strTeamFanart2 : String?,
    @SerializedName("strTeamFanart3") val strTeamFanart3 : String?,
    @SerializedName("strTeamFanart4") val strTeamFanart4 : String?
)