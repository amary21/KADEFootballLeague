package com.amary.kade_footballeague.rest.response

import com.amary.kade_footballeague.rest.response.model.Events
import com.google.gson.annotations.SerializedName

data class EventsResponse (
    @SerializedName("events") val events : List<Events>?
)