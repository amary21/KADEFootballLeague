package com.amary.kade_footballeague.data.local.model

data class NextMatch (
    val id:Long?,
    val idEvent : String?,
    val strEvent : String?,
    val strHomeTeam : String?,
    val strAwayTeam : String?,
    val dateEvent : String?,
    val strTime : String?,
    val imgHomeBadge: String?,
    val imgAwayBadge: String?
){
    companion object{
        const val TABLE_NEXT: String = "TABLE_NEXT"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
        const val STR_EVENT: String = "STR_EVENT"
        const val STR_HOME_TEAM: String = "STR_HOME_TEAM"
        const val STR_AWAY_TEAM: String = "STR_AWAY_TEAM"
        const val DATE_EVENT: String = "DATE_EVENT"
        const val STR_TIME: String = "STR_TIME"
        const val IMG_HOME_BADGE: String = "IMG_HOME_BADGE"
        const val IMG_AWAY_BADGE: String = "IMG_AWAY_BADGE"
    }
}