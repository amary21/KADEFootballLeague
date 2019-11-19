package com.amary.kade_footballeague.ui.jadwal_list.next

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amary.kade_footballeague.rest.ApiRepository
import com.amary.kade_footballeague.rest.response.EventsResponse

class NextViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    fun getNextMatch(id : String) : LiveData<EventsResponse> {
        return apiRepository.getNextMatchEvent(id)
    }

}
