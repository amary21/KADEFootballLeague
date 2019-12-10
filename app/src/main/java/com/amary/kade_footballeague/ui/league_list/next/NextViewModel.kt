package com.amary.kade_footballeague.ui.league_list.next

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.EventsResponse
import com.amary.kade_footballeague.data.rest.response.TeamsResponse

class NextViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    fun getNextMatch(id: String): LiveData<EventsResponse> {
        return apiRepository.getNextMatchEvent(id)
    }

    fun getHomeTeam(id: String): LiveData<TeamsResponse> {
        return apiRepository.getTeam(id)
    }

    fun getAwayTeam(id: String): LiveData<TeamsResponse> {
        return apiRepository.getTeam(id)
    }

    fun statusNetwork(): LiveData<Boolean?> {
        return apiRepository.networking()
    }

}
