package com.amary.kade_footballeague.ui.detail_league

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.LeagueDetResponse

class DetailScheduleViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getLeagueDetail(id: String): LiveData<LeagueDetResponse> {
        return apiRepository.getLeagueDetail(id)
    }

    fun statusNetwork() : LiveData<Boolean?> {
        return apiRepository.networking()
    }
}