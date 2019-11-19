package com.amary.kade_footballeague.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amary.kade_footballeague.rest.ApiRepository
import com.amary.kade_footballeague.rest.response.LeagueResponse

class MainViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getLeague() : LiveData<LeagueResponse>{
        return apiRepository.getLeague()
    }
}