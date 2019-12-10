package com.amary.kade_footballeague.ui.detail_league

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.LeagueDetResponse
import com.amary.kade_footballeague.utils.RxImmediateSchedulerRule
import com.amary.kade_footballeague.utils.mock
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class DetailLeagueViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    private lateinit var viewModel: DetailLeagueViewModel

    private val apiRepository = Mockito.mock(ApiRepository::class.java)

    @Before
    fun setUp(){
        viewModel = DetailLeagueViewModel(apiRepository)
    }


    @Test
    fun getLeagueDetail() {
        val id = "4328"

        val league = LeagueDetResponse(mutableListOf())
        val leagueDetResponse = MutableLiveData<LeagueDetResponse>()
        leagueDetResponse.value = league

        `when`(apiRepository.getLeagueDetail(id)).thenReturn(leagueDetResponse)

        val observer = mock<Observer<LeagueDetResponse>>()
        viewModel.getLeagueDetail(id).observeForever(observer)
        verify(observer).onChanged(league)
    }

    @Test
    fun statusNetwork() {
        val status =  MutableLiveData<Boolean>()
        `when`(apiRepository.networking()).thenReturn(status)

        val observer = mock<Observer<Boolean?>>()
        viewModel.statusNetwork().observeForever(observer)
        verify(apiRepository).networking()
    }
}