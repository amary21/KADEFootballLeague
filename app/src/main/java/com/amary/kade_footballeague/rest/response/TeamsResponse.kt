package com.amary.kade_footballeague.rest.response

import com.amary.kade_footballeague.rest.response.model.Teams
import com.google.gson.annotations.SerializedName

data class TeamsResponse(
    @SerializedName("teams") val teams : List<Teams>
)