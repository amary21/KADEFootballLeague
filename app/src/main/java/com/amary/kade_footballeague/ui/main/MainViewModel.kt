package com.amary.kade_footballeague.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.LeagueDetResponse
import com.amary.kade_footballeague.data.rest.response.LeagueResponse

class MainViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getLeague(): LiveData<LeagueResponse> {
        return apiRepository.getLeague()
    }

    fun statusNetwork(): LiveData<Boolean?> {
        return apiRepository.networking()
    }

    fun getIconLeagues(id: String): LiveData<LeagueDetResponse> {
        return apiRepository.getLeagueDetail(id)
    }
}