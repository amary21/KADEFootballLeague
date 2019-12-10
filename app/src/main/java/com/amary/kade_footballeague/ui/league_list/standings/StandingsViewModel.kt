package com.amary.kade_footballeague.ui.league_list.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.StandingsResponse
import com.amary.kade_footballeague.data.rest.response.TeamsResponse

class StandingsViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getStandingLeague(id : String) : LiveData<StandingsResponse>{
        return apiRepository.getStandingsLeague(id)
    }

    fun getBadgeTeam(id: String): LiveData<TeamsResponse> {
        return apiRepository.getTeam(id)
    }


    fun statusNetwork(): LiveData<Boolean?> {
        return apiRepository.networking()
    }

}
