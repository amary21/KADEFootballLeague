package com.amary.kade_footballeague.data.local.model

data class PrevMatch (
    val id:Long?,
    val idEvent : String?,
    val strEvent : String?,
    val strHomeTeam : String?,
    val strAwayTeam : String?,
    val intHomeScore : String?,
    val intAwayScore : String?,
    val dateEvent : String?,
    val strTime : String?,
    val imgHomeBadge: String?,
    val imgAwayBadge: String?
){
    companion object{
        const val TABLE_PREV: String = "TABLE_PREV"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
        const val STR_EVENT: String = "STR_EVENT"
        const val STR_HOME_TEAM: String = "STR_HOME_TEAM"
        const val STR_AWAY_TEAM: String = "STR_AWAY_TEAM"
        const val INT_HOME_SCORE : String = "INT_HOME_SCORE"
        const val INT_AWAY_SCORE : String = "INT_AWAY_SCORE"
        const val DATE_EVENT: String = "DATE_EVENT"
        const val STR_TIME: String = "STR_TIME"
        const val IMG_HOME_BADGE: String = "IMG_HOME_BADGE"
        const val IMG_AWAY_BADGE: String = "IMG_AWAY_BADGE"
    }
}