package com.amary.kade_footballeague.ui.jadwal_list.previous

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amary.kade_footballeague.rest.ApiRepository
import com.amary.kade_footballeague.rest.response.EventsResponse

class PreviousViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getPrevMatch(id : String) : LiveData<EventsResponse> {
        return apiRepository.getPrevMatchEvent(id)
    }
}
