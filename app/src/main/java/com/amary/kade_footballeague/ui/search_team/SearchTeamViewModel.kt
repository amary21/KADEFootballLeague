package com.amary.kade_footballeague.ui.search_team

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.TeamsResponse

class SearchTeamViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getSearchTeam(query: String) : LiveData<TeamsResponse>{
        return apiRepository.getSearchTeam(query)
    }

    fun statusNetwork(): LiveData<Boolean?> {
        return apiRepository.networking()
    }
}