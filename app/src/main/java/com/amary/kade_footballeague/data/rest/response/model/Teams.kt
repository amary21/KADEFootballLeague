package com.amary.kade_footballeague.data.rest.response.model

import com.google.gson.annotations.SerializedName

data class Teams (
    @SerializedName("strTeam") val strTeam : String,
    @SerializedName("strTeamBadge") val strTeamBadge: String
)