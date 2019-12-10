package com.amary.kade_footballeague.data.local.model

data class TeamFav(
    val id:Long?,
    val idTeam: String?,
    val strTeam: String?,
    val strTeamBadge: String?
) {
    companion object{
        const val TABLE_TEAM: String = "TABLE_TEAM"
        const val ID: String = "ID_"
        const val ID_TEAM: String = "ID_TEAM"
        const val STR_TEAM: String = "STR_TEAM"
        const val STR_TEAM_BADGE ="STR_TEAM_BADGE"
    }
}