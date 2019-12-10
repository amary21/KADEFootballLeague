package com.amary.kade_footballeague.data.rest.response

import com.amary.kade_footballeague.data.rest.response.model.Leagues
import com.google.gson.annotations.SerializedName

data class LeagueResponse(
    @SerializedName("leagues") val leagues: List<Leagues>?
)