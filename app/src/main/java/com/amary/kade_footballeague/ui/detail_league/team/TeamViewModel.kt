package com.amary.kade_footballeague.ui.detail_league.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.TeamsResponse

class TeamViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getTeamAll(id: String): LiveData<TeamsResponse> {
        return apiRepository.getTeamAll(id)
    }

    fun statusNetwork() : LiveData<Boolean?> {
        return apiRepository.networking()
    }
}
