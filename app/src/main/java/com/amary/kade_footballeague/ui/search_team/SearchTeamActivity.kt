package com.amary.kade_footballeague.ui.search_team

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
import androidx.recyclerview.widget.GridLayoutManager
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.rest.ApiClient
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.model.Teams
import kotlinx.android.synthetic.main.activity_search_team.*

@Suppress("UNCHECKED_CAST")
class SearchTeamActivity : AppCompatActivity() {

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var teamViewModel: SearchTeamViewModel
    private var teamAdapter: SearchTeamAdapter? = null
    private val listSearchTeam = ArrayList<Teams>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_team)

        apiRepository = ApiRepository(apiService)
        teamAdapter = SearchTeamAdapter(this)
        teamViewModel = getViewModel(apiRepository)

        rvSearchTeam.layoutManager = GridLayoutManager(this, 2)
        rvSearchTeam.setHasFixedSize(true)
        rvSearchTeam.isNestedScrollingEnabled = false
        rvSearchTeam.adapter = teamAdapter

        rvSearchTeam.visibility = View.GONE
        imgSearch404Team.visibility = View.VISIBLE
        txtSearch404Team.visibility = View.VISIBLE
    }

    private fun getViewModel(apiRepository: ApiRepository): SearchTeamViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SearchTeamViewModel(apiRepository) as T
            }

        })[SearchTeamViewModel::class.java]
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
                    getSearchTeam(newText)
                    rvSearchTeam.visibility = View.VISIBLE
                    imgSearch404Team.visibility = View.GONE
                    txtSearch404Team.visibility = View.GONE
                }else{
                    getSearchTeam("")
                    rvSearchTeam.visibility = View.VISIBLE
                    imgSearch404Team.visibility = View.GONE
                    txtSearch404Team.visibility = View.GONE

                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return true
    }

    private fun getSearchTeam(search: String) {
        listSearchTeam.clear()
        teamViewModel.getSearchTeam(search).observe(this, Observer {
            if (it != null) {
                for (item in it.teams) {
                    if (item.strSport == "Soccer") {
                                    listSearchTeam.add(
                                        Teams(
                                            item.idTeam,
                                            item.strTeam,
                                            item.strSport,
                                            item.strLeague,
                                            item.idLeague,
                                            item.strStadium,
                                            item.strStadiumThumb,
                                            item.strStadiumDescription,
                                            item.strDescriptionEN,
                                            item.strTeamBadge,
                                            item.strTeamJersey,
                                            item.strTeamFanart1,
                                            item.strTeamFanart2,
                                            item.strTeamFanart3,
                                            item.strTeamFanart4
                                        )
                                    )

                                    teamAdapter?.setTeam(listSearchTeam)
                                    Log.e("DATA SEARCH", item.strTeam)
                    }
                }
            }
        })

        teamViewModel.statusNetwork().observe(this, Observer {
            if (!it!!) {
                Toast.makeText(this, "Connection error or data not found", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}
