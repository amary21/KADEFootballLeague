package com.amary.kade_footballeague.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.LeagueDetResponse
import com.amary.kade_footballeague.data.rest.response.LeagueResponse
import com.amary.kade_footballeague.utils.RxImmediateSchedulerRule
import com.amary.kade_footballeague.utils.mock
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


class MainViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    private lateinit var viewModel: MainViewModel

    private val apiRepository = mock(ApiRepository::class.java)

    @Before
    fun setUp() {
        viewModel = MainViewModel(apiRepository)
    }

    @Test
    fun getLeague() {
        val leagues = LeagueResponse(mutableListOf())
        val leagueResponse = MutableLiveData<LeagueResponse>()
        leagueResponse.value = leagues

        `when`(apiRepository.getLeague()).thenReturn(leagueResponse)

        val observer = mock<Observer<LeagueResponse>>()
        viewModel.getLeague().observeForever(observer)
        verify(observer).onChanged(leagues)
    }

    @Test
    fun statusNetwork() {
        val status = MutableLiveData<Boolean>()
        `when`(apiRepository.networking()).thenReturn(status)

        val observer = mock<Observer<Boolean?>>()
        viewModel.statusNetwork().observeForever(observer)
        verify(apiRepository).networking()
    }

    @Test
    fun getIconLeagues() {
        val id = "4328"

        val leagues = LeagueDetResponse(mutableListOf())
        val leagueDetResponse = MutableLiveData<LeagueDetResponse>()
        leagueDetResponse.value = leagues

        `when`(apiRepository.getLeagueDetail(id)).thenReturn(leagueDetResponse)

        val observer = mock<Observer<LeagueDetResponse>>()
        viewModel.getIconLeagues(id).observeForever(observer)
        verify(observer).onChanged(leagues)
    }
}