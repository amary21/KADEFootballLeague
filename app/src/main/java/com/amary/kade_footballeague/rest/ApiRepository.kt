package com.amary.kade_footballeague.rest

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amary.kade_footballeague.rest.response.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApiRepository(private val apiInterface: ApiInterface) {

    private val network = MutableLiveData<Boolean>()

    fun networking(): LiveData<Boolean?> {
        return network
    }

    @SuppressLint("CheckResult")
    fun getLeague() : LiveData<LeagueResponse>{
        val dataLeague = MutableLiveData<LeagueResponse>()
        apiInterface.getLeague()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null){
                    dataLeague.value = it
                    network.value = true
                }
            },{
                Log.e("ERROR", it.message.toString())
                network.value = false
            })

        return dataLeague
    }

    @SuppressLint("CheckResult")
    fun getLeagueDetail(id : String) : LiveData<LeagueDetResponse>{
        val dataLeague = MutableLiveData<LeagueDetResponse>()
        apiInterface.getLeagueById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null){
                    dataLeague.value = it
                    network.value = true
                }
            },{
                if (it.message != null){
                    Log.e("ERROR DETAIL", it.message.toString())
                    network.value = false
                }
            })
        return dataLeague
    }

    @SuppressLint("CheckResult")
    fun getNextMatchEvent(id : String) : LiveData<EventsResponse>{
        val dataEvent = MutableLiveData<EventsResponse>()
        apiInterface.getNextMatchEvent(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null){
                    dataEvent.value = it
                    network.value = true
                }
            },{
                Log.e("NEXT ERROR", it.message.toString())
                network.value = false
            })
        return dataEvent
    }

    @SuppressLint("CheckResult")
    fun getPrevMatchEvent(id : String) : LiveData<EventsResponse>{
        val dataEvent = MutableLiveData<EventsResponse>()
        apiInterface.getPreviousMatchEvent(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null){
                    dataEvent.value = it
                    network.value = true
                }
            },{
                Log.e("NEXT ERROR", it.message.toString())
                network.value = false
            })
        return dataEvent
    }

    @SuppressLint("CheckResult")
    fun getDetailMatch(id : String) : LiveData<EventsResponse>{
        val dataEvent = MutableLiveData<EventsResponse>()
        apiInterface.getDetailMatch(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null){
                    dataEvent.value = it
                    network.value = true
                }
            },{
                network.value = false
                Log.e("NEXT ERROR", it.message.toString())
            })
        return dataEvent
    }

    @SuppressLint("CheckResult")
    fun getTeam(id : String) : LiveData<TeamsResponse>{
        val dataTeams = MutableLiveData<TeamsResponse>()
        apiInterface.getTeams(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null){
                    dataTeams.value = it
                    network.value = true
                }
            },{
                Log.e("NEXT ERROR", it.message.toString())
                network.value = false
            })
        return dataTeams
    }

    @SuppressLint("CheckResult")
    fun getSearchEvent(search: String): LiveData<SearchResponse> {
        val dataSearch = MutableLiveData<SearchResponse>()
        apiInterface.getSearchEvent(search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    dataSearch.value = it
                    network.value = true
                }
            }, {
                Log.e("Search ERROR", it.message.toString())
                network.value = false
            })

        return dataSearch

    }
}