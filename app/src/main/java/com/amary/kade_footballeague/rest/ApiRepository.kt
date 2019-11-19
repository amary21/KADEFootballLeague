package com.amary.kade_footballeague.rest

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amary.kade_footballeague.rest.response.EventsResponse
import com.amary.kade_footballeague.rest.response.LeagueDetResponse
import com.amary.kade_footballeague.rest.response.LeagueResponse
import com.amary.kade_footballeague.rest.response.TeamsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApiRepository(private val apiInterface: ApiInterface) {

    @SuppressLint("CheckResult")
    fun getLeague() : LiveData<LeagueResponse>{
        val dataLeague = MutableLiveData<LeagueResponse>()
        apiInterface.getLeague()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null){
                    dataLeague.value = it
                }
            },{
                Log.e("ERROR", it.message.toString())
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
                }
            },{
                Log.e("ERROR DETAIL", it.message.toString())
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
                }
            },{
                Log.e("NEXT ERROR", it.message.toString())
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
                }
            },{
                Log.e("NEXT ERROR", it.message.toString())
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
                }
            },{
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
                }
            },{
                Log.e("NEXT ERROR", it.message.toString())
            })
        return dataTeams
    }
}