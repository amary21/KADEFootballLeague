package com.amary.kade_footballeague.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amary.kade_footballeague.rest.ApiRepository
import com.amary.kade_footballeague.rest.response.SearchResponse
import com.amary.kade_footballeague.rest.response.TeamsResponse

class SearchViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    fun getSearchEvent(search: String): LiveData<SearchResponse> {
        return apiRepository.getSearchEvent(search)
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