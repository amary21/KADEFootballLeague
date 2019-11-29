package com.amary.kade_footballeague.data.rest

import com.amary.kade_footballeague.data.rest.response.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("all_leagues.php")
    fun getLeague(): Observable<LeagueResponse>

    @GET("lookupleague.php")
    fun getLeagueById(@Query("id") id: String
    ): Observable<LeagueDetResponse>

    @GET("eventsnextleague.php")
    fun getNextMatchEvent(@Query("id")id : String
    ) : Observable<EventsResponse>

    @GET("eventspastleague.php")
    fun getPreviousMatchEvent(@Query("id")id : String
    ) : Observable<EventsResponse>

    @GET("lookupevent.php")
    fun getDetailMatch(@Query("id")id :String
    ) : Observable<EventsResponse>

    @GET("lookupteam.php")
    fun getTeams(@Query("id")id :String
    ) : Observable<TeamsResponse>

    @GET("searchevents.php")
    fun getSearchEvent(
        @Query("e") e: String
    ): Observable<SearchResponse>
}