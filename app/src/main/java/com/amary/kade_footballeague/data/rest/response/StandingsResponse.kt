package com.amary.kade_footballeague.data.rest.response

import com.amary.kade_footballeague.data.rest.response.model.Table
import com.google.gson.annotations.SerializedName

data class StandingsResponse (
    @SerializedName("table") val table : List<Table>?
)