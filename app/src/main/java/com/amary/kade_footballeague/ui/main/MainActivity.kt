package com.amary.kade_footballeague.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.rest.ApiClient
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.response.model.ListLeagues
import com.amary.kade_footballeague.ui.league_favorite.LeagueFavActivity
import com.amary.kade_footballeague.ui.search_event.SearchEventActivity
import com.amary.kade_footballeague.ui.search_team.SearchTeamActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: MainViewModel
    private var mainAdapter : MainAdapter? = null
    private val listLeagues = ArrayList<ListLeagues>()

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showLayout = AnimationUtils.loadAnimation(this, R.anim.show_layout)
        val hideLayout = AnimationUtils.loadAnimation(this, R.anim.hide_layout)

        apiRepository = ApiRepository(apiService)
        mainAdapter = MainAdapter(this)
        viewModel = getViewModel(apiRepository)

        rvLeague.layoutManager = LinearLayoutManager(this)
        rvLeague.setHasFixedSize(true)
        rvLeague.isNestedScrollingEnabled = false
        rvLeague.adapter = mainAdapter

        viewModel.getLeague().observe(this, Observer {
            if (it != null){
                for (item in it.leagues!!) {
                    if (item.strSport == "Soccer") {
                        viewModel.getIconLeagues(item.idLeague).observe(this, Observer { icon ->
                            if (icon.leagues[0].strBadge != null) {
                                listLeagues.add(
                                    ListLeagues(
                                        item.idLeague,
                                        item.strLeague,
                                        item.strSport,
                                        icon.leagues[0].strBadge
                                    )
                                )
                            } else {
                                listLeagues.add(
                                    ListLeagues(
                                        item.idLeague,
                                        item.strLeague,
                                        item.strSport,
                                        null
                                    )
                                )
                            }

                            mainAdapter?.setLeagues(listLeagues)
                            imgCover.visibility = View.VISIBLE
                            txtCover.visibility = View.VISIBLE
                            rvLeague.visibility = View.VISIBLE
                            smMain.stopShimmer()
                            smMain.visibility = View.GONE
                            fabSearch.visibility = View.VISIBLE
                            fabFavoriteEvent.visibility = View.VISIBLE
                        })
                    }
                }
            }

        })

        viewModel.statusNetwork().observe(this, Observer {
            if (!it!!) {
                Toast.makeText(this, "Connection error or data not found", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        fabSearch.setOnClickListener {
            if (lySearchEvent.visibility == View.VISIBLE && lySearchTeam.visibility == View.VISIBLE){
                lySearchEvent.visibility = View.GONE
                lySearchTeam.visibility = View.GONE
                lySearchEvent.startAnimation(hideLayout)
                lySearchTeam.startAnimation(hideLayout)
            }else{
                lySearchEvent.visibility = View.VISIBLE
                lySearchTeam.visibility = View.VISIBLE
                lySearchEvent.startAnimation(showLayout)
                lySearchTeam.startAnimation(showLayout)
            }
        }

        fabSearchEvent.setOnClickListener {
            if (lySearchEvent.visibility == View.VISIBLE && lySearchTeam.visibility == View.VISIBLE){
                lySearchEvent.visibility = View.GONE
                lySearchTeam.visibility = View.GONE
                lySearchEvent.startAnimation(hideLayout)
                lySearchTeam.startAnimation(hideLayout)
            }

            startActivity<SearchEventActivity>()
        }

        fabSearchTeam.setOnClickListener {
            if (lySearchEvent.visibility == View.VISIBLE && lySearchTeam.visibility == View.VISIBLE){
                lySearchEvent.visibility = View.GONE
                lySearchTeam.visibility = View.GONE
                lySearchEvent.startAnimation(hideLayout)
                lySearchTeam.startAnimation(hideLayout)
            }
            startActivity<SearchTeamActivity>()
        }

        fabFavoriteEvent.setOnClickListener {
            if (lySearchEvent.visibility == View.VISIBLE && lySearchTeam.visibility == View.VISIBLE){
                lySearchEvent.visibility = View.GONE
                lySearchTeam.visibility = View.GONE
                lySearchEvent.startAnimation(hideLayout)
                lySearchTeam.startAnimation(hideLayout)
            }
            startActivity<LeagueFavActivity>()
        }
    }

    private fun getViewModel(apiRepository: ApiRepository): MainViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(apiRepository) as T
            }

        })[MainViewModel::class.java]
    }
}
