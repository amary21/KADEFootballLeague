package com.amary.kade_footballeague.data.rest.response

import com.amary.kade_footballeague.data.rest.response.model.LeagueDet
import com.google.gson.annotations.SerializedName

class LeagueDetResponse (
    @SerializedName("leagues") val leagues : List<LeagueDet>
)