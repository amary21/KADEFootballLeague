package com.amary.kade_footballeague.ui.jadwal_list.next

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amary.kade_footballeague.rest.ApiRepository
import com.amary.kade_footballeague.rest.response.EventsResponse
import com.amary.kade_footballeague.rest.response.TeamsResponse

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
