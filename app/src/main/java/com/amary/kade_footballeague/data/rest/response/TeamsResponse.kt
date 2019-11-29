package com.amary.kade_footballeague.data.rest.response

import com.amary.kade_footballeague.data.rest.response.model.Teams
import com.google.gson.annotations.SerializedName

data class TeamsResponse(
    @SerializedName("teams") val teams : List<Teams>
)