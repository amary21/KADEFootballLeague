package com.amary.kade_footballeague.ui.detail_team

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.TeamsResponse

class DetailTeamViewModel(private val apiRepository: ApiRepository) : ViewModel(){

    fun getDetailTeam(id: String): LiveData<TeamsResponse> {
        return apiRepository.getDetailTeam(id)
    }

    fun statusNetwork() : LiveData<Boolean?> {
        return apiRepository.networking()
    }
}