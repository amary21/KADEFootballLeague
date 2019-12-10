package com.amary.kade_footballeague.ui.search_event

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.rest.ApiClient
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.model.SchedulesMatch
import kotlinx.android.synthetic.main.activity_search_event.*

@Suppress("UNCHECKED_CAST")
class SearchEventActivity : AppCompatActivity() {

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var eventViewModel: SearchEventViewModel
    private var eventAdapter: SearchEventAdapter? = null
    private val listSearchEvent = ArrayList<SchedulesMatch>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_event)

        apiRepository = ApiRepository(apiService)
        eventAdapter = SearchEventAdapter(this)
        eventViewModel = getViewModel(apiRepository)

        rvSearch.layoutManager = LinearLayoutManager(this)
        rvSearch.setHasFixedSize(true)
        rvSearch.isNestedScrollingEnabled = false
        rvSearch.adapter = eventAdapter

        rvSearch.visibility = View.GONE
        imgSearch404.visibility = View.VISIBLE
        txtSearch404.visibility = View.VISIBLE
    }

    private fun getSearchEvent(search: String) {
        listSearchEvent.clear()
        eventViewModel.getSearchEvent(search).observe(this, Observer {
            if (it != null) {
                for (item in it.events) {
                    if (item.strSport == "Soccer") {
                        eventViewModel.getHomeTeam(item.idHomeTeam).observe(this, Observer { iconHome ->
                            eventViewModel.getAwayTeam(item.idAwayTeam)
                                .observe(this, Observer { iconAway ->
                                    listSearchEvent.add(
                                        SchedulesMatch(
                                            item.idEvent,
                                            item.strEvent!!,
                                            item.strHomeTeam,
                                            item.strAwayTeam,
                                            item.intHomeScore,
                                            item.intAwayScore,
                                            item.dateEvent,
                                            item.strTime,
                                            item.idHomeTeam,
                                            item.idAwayTeam,
                                            iconHome.teams[0].strTeamBadge,
                                            iconAway.teams[0].strTeamBadge
                                        )
                                    )

                                    eventAdapter?.setEvent(listSearchEvent)
                                    Log.e("DATA SEARCH", item.strEvent.toString())

                                })
                        })
                    }
                }
            }
        })

        eventViewModel.statusNetwork().observe(this, Observer {
            if (!it!!) {
                Toast.makeText(this, "Connection error or data not found", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun getViewModel(apiRepository: ApiRepository): SearchEventViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SearchEventViewModel(apiRepository) as T
            }

        })[SearchEventViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_app_bar, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.mn_app_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("DefaultLocale")
            override fun onQueryTextSubmit(newText: String?): Boolean {
                newText?.toLowerCase()
                if (newText != null) {
                    getSearchEvent(newText)
                    rvSearch.visibility = View.VISIBLE
                    imgSearch404.visibility = View.GONE
                    txtSearch404.visibility = View.GONE
                }else{
                    getSearchEvent("")
                    rvSearch.visibility = View.VISIBLE
                    imgSearch404.visibility = View.GONE
                    txtSearch404.visibility = View.GONE

                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return true
    }

}
