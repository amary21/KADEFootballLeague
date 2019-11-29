package com.amary.kade_footballeague.data.rest.response.model

import com.google.gson.annotations.SerializedName

data class Events (
    @SerializedName("idEvent") val idEvent : String,
    @SerializedName("strEvent") val strEvent : String?,
    @SerializedName("strSport") val strSport : String,
    @SerializedName("strHomeTeam") val strHomeTeam : String,
    @SerializedName("strAwayTeam") val strAwayTeam : String,
    @SerializedName("intHomeScore") val intHomeScore : String? = null,
    @SerializedName("intAwayScore") val intAwayScore : String? = null,
    @SerializedName("strHomeGoalDetails") val strHomeGoalDetails : String,
    @SerializedName("strHomeRedCards") val strHomeRedCards : String,
    @SerializedName("strHomeYellowCards") val strHomeYellowCards : String,
    @SerializedName("strHomeLineupGoalkeeper") val strHomeLineupGoalkeeper : String,
    @SerializedName("strHomeLineupDefense") val strHomeLineupDefense : String,
    @SerializedName("strHomeLineupMidfield") val strHomeLineupMidfield : String,
    @SerializedName("strHomeLineupForward") val strHomeLineupForward : String,
    @SerializedName("strHomeLineupSubstitutes") val strHomeLineupSubstitutes : String,
    @SerializedName("strHomeFormation") val strHomeFormation : String,
    @SerializedName("strAwayRedCards") val strAwayRedCards : String,
    @SerializedName("strAwayYellowCards") val strAwayYellowCards : String,
    @SerializedName("strAwayGoalDetails") val strAwayGoalDetails : String,
    @SerializedName("strAwayLineupGoalkeeper") val strAwayLineupGoalkeeper : String,
    @SerializedName("strAwayLineupDefense") val strAwayLineupDefense : String,
    @SerializedName("strAwayLineupMidfield") val strAwayLineupMidfield : String,
    @SerializedName("strAwayLineupForward") val strAwayLineupForward : String,
    @SerializedName("strAwayLineupSubstitutes") val strAwayLineupSubstitutes : String,
    @SerializedName("strAwayFormation") val strAwayFormation : String,
    @SerializedName("dateEvent") val dateEvent : String,
    @SerializedName("dateEventLocal") val dateEventLocal : String,
    @SerializedName("strDate") val strDate : String,
    @SerializedName("strTime") val strTime : String,
    @SerializedName("strTimeLocal") val strTimeLocal : String,
    @SerializedName("strTVStation") val strTVStation : String,
    @SerializedName("idHomeTeam") val idHomeTeam : String,
    @SerializedName("idAwayTeam") val idAwayTeam : String,
    @SerializedName("strThumb") val strThumb : String,
    @SerializedName("strVideo") val strVideo: String
)