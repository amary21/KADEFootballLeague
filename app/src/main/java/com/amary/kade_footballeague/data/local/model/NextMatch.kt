package com.amary.kade_footballeague.data.local.model

data class NextMatch (
    val idEvent : String,
    val strEvent : String,
    val strHomeTeam : String,
    val strAwayTeam : String,
    val intHomeScore : String? = null,
    val intAwayScore : String? = null,
    val dateEvent : String,
    val strTime : String,
    val idHomeTeam : String,
    val idAwayTeam : String,
    val imgHomeBadge: String? = null,
    val imgAwayBadge: String? = null
)