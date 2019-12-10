package com.amary.kade_footballeague.data.rest.response.model

import com.google.gson.annotations.SerializedName

data class Table (
    @SerializedName("name") val name : String,
    @SerializedName("teamid") val teamid : String,
    @SerializedName("played") val played : String,
    @SerializedName("goalsfor") val goalsfor : String,
    @SerializedName("goalsagainst") val goalsagainst : String,
    @SerializedName("goalsdifference") val goalsdifference : String,
    @SerializedName("win") val win : String,
    @SerializedName("draw") val draw : String,
    @SerializedName("loss") val loss : String,
    @SerializedName("total") val total : String,
    val imgBadge : String? =null
)