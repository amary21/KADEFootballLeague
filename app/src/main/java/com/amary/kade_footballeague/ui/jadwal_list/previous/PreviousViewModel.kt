package com.amary.kade_footballeague.ui.jadwal_list.previous

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.EventsResponse
import com.amary.kade_footballeague.data.rest.response.TeamsResponse

class PreviousViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getPrevMatch(id: String): LiveData<EventsResponse> {
        return apiRepository.getPrevMatchEvent(id)
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
