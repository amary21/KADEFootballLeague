package com.amary.kade_footballeague.ui.search_event

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.SearchResponse
import com.amary.kade_footballeague.data.rest.response.TeamsResponse
import com.amary.kade_footballeague.utils.RxImmediateSchedulerRule
import com.amary.kade_footballeague.utils.mock
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class SearchEventViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    private lateinit var eventViewModel: SearchEventViewModel

    private val apiRepository = Mockito.mock(ApiRepository::class.java)

    @Before
    fun setUp() {
        eventViewModel = SearchEventViewModel(apiRepository)
    }


    @Test
    fun getSearchEvent() {
        val search = "wolves"

        val searched = SearchResponse(mutableListOf())
        val searchResponse = MutableLiveData<SearchResponse>()
        searchResponse.value = searched

        Mockito.`when`(apiRepository.getSearchEvent(search)).thenReturn(searchResponse)

        val observer = mock<Observer<SearchResponse>>()
        eventViewModel.getSearchEvent(search).observeForever(observer)
        Mockito.verify(observer).onChanged(searched)
    }

    @Test
    fun getHomeTeam() {
        val id = "133599"

        val team = TeamsResponse(mutableListOf())
        val teamsResponse = MutableLiveData<TeamsResponse>()
        teamsResponse.value = team

        Mockito.`when`(apiRepository.getTeam(id)).thenReturn(teamsResponse)

        val observer = mock<Observer<TeamsResponse>>()
        eventViewModel.getHomeTeam(id).observeForever(observer)
        Mockito.verify(observer).onChanged(team)
    }

    @Test
    fun getAwayTeam() {
        val id = "133636"

        val team = TeamsResponse(mutableListOf())
        val teamsResponse = MutableLiveData<TeamsResponse>()
        teamsResponse.value = team

        Mockito.`when`(apiRepository.getTeam(id)).thenReturn(teamsResponse)

        val observer = mock<Observer<TeamsResponse>>()
        eventViewModel.getHomeTeam(id).observeForever(observer)
        Mockito.verify(observer).onChanged(team)
    }

    @Test
    fun statusNetwork() {
        val status = MutableLiveData<Boolean>()
        Mockito.`when`(apiRepository.networking()).thenReturn(status)

        val observer = mock<Observer<Boolean?>>()
        eventViewModel.statusNetwork().observeForever(observer)
        Mockito.verify(apiRepository).networking()
    }
}