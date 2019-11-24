package com.amary.kade_footballeague.ui.detail_liga

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amary.kade_footballeague.rest.ApiRepository
import com.amary.kade_footballeague.rest.response.LeagueDetResponse

class DetailLigaViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getLeagueDetail(id: String): LiveData<LeagueDetResponse> {
        return apiRepository.getLeagueDetail(id)
    }

    fun statusNetwork() : LiveData<Boolean?> {
        return apiRepository.networking()
    }
}