package com.amary.kade_footballeague.ui.jadwal_list.previous

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.rest.ApiClient
import com.amary.kade_footballeague.rest.ApiRepository
import com.amary.kade_footballeague.rest.ID_LEAGUE
import com.amary.kade_footballeague.rest.response.model.SchedulesMatch
import kotlinx.android.synthetic.main.fragment_previous.*

@Suppress("UNCHECKED_CAST")
class PreviousFragment : Fragment() {

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: PreviousViewModel
    private var prevAdapter : PreviousAdapter? = null
    private val listPrevMatch = ArrayList<SchedulesMatch>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_previous, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = this.arguments
        val idLeague = bundle?.getString(ID_LEAGUE)

        apiRepository = ApiRepository(apiService)
        viewModel = getViewModel(apiRepository)
        prevAdapter = PreviousAdapter(context as Activity)

        rvPrevious.layoutManager = LinearLayoutManager(context)
        rvPrevious.setHasFixedSize(true)
        rvPrevious.adapter = prevAdapter

        if (idLeague != null) {
            viewModel.getPrevMatch(idLeague).observe(this, Observer {
                if (it.events != null){
                    for (item in it.events) {
                        viewModel.getHomeTeam(item.idHomeTeam).observe(this, Observer { iconHome ->
                            viewModel.getAwayTeam(item.idAwayTeam)
                                .observe(this, Observer { iconAway ->
                                    listPrevMatch.add(
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

                                    prevAdapter?.setEvent(listPrevMatch)
                                    rvPrevious.visibility = View.VISIBLE
                                    smPrevMatch.stopShimmer()
                                    smPrevMatch.visibility = View.GONE
                                })
                        })
                    }
                }
            })

            viewModel.statusNetwork().observe(this, Observer {
                if (!it!!) {
                    Toast.makeText(
                        activity,
                        "Connection error or data not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun getViewModel(apiRepository: ApiRepository): PreviousViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return PreviousViewModel(apiRepository) as T
            }

        })[PreviousViewModel::class.java]
    }

}
