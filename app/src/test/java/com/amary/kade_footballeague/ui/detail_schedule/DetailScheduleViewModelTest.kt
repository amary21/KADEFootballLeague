package com.amary.kade_footballeague.ui.detail_schedule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.EventsResponse
import com.amary.kade_footballeague.data.rest.response.TeamsResponse
import com.amary.kade_footballeague.utils.RxImmediateSchedulerRule
import com.amary.kade_footballeague.utils.mock
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class DetailScheduleViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    private lateinit var viewModel: DetailScheduleViewModel

    private val apiRepository = Mockito.mock(ApiRepository::class.java)

    @Before
    fun setUp() {
        viewModel = DetailScheduleViewModel(apiRepository)
    }


    @Test
    fun getLeagueDetail() {
        val id = "4328"

        val event = EventsResponse(mutableListOf())
        val eventsResponse = MutableLiveData<EventsResponse>()
        eventsResponse.value = event

        `when`(apiRepository.getDetailMatch(id)).thenReturn(eventsResponse)

        val observer = mock<Observer<EventsResponse>>()
        viewModel.getLeagueDetail(id).observeForever(observer)
        verify(observer).onChanged(event)
    }

    @Test
    fun getHomeTeam() {
        val id = "133599"

        val team = TeamsResponse(mutableListOf())
        val teamsResponse = MutableLiveData<TeamsResponse>()
        teamsResponse.value = team

        `when`(apiRepository.getTeam(id)).thenReturn(teamsResponse)

        val observer = mock<Observer<TeamsResponse>>()
        viewModel.getHomeTeam(id).observeForever(observer)
        verify(observer).onChanged(team)
    }

    @Test
    fun getAwayTeam() {
        val id = "133636"

        val teams = TeamsResponse(mutableListOf())
        val teamsResponse = MutableLiveData<TeamsResponse>()
        teamsResponse.value = teams

        `when`(apiRepository.getTeam(id)).thenReturn(teamsResponse)

        val observer = mock<Observer<TeamsResponse>>()
        viewModel.getAwayTeam(id).observeForever(observer)
        verify(observer).onChanged(teams)
    }

    @Test
    fun statusNetwork() {
        val status = MutableLiveData<Boolean>()
        `when`(apiRepository.networking()).thenReturn(status)

        val observer = mock<Observer<Boolean?>>()
        viewModel.statusNetwork().observeForever(observer)
        verify(apiRepository).networking()
    }
}