package com.amary.kade_footballeague.ui.jadwal_list.next

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
import com.amary.kade_footballeague.data.rest.ApiClient
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.ID_LEAGUE
import com.amary.kade_footballeague.data.rest.response.model.SchedulesMatch
import kotlinx.android.synthetic.main.fragment_next.*

@Suppress("UNCHECKED_CAST")
class NextFragment : Fragment() {

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: NextViewModel
    private var nextAdapter : NextAdapter? = null
    private val listNextMatch = ArrayList<SchedulesMatch>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_next, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = this.arguments
        val idLeague = bundle?.getString(ID_LEAGUE)

        apiRepository = ApiRepository(apiService)
        viewModel = getViewModel(apiRepository)
        nextAdapter = NextAdapter(context as Activity)

        rvNext.layoutManager = LinearLayoutManager(context)
        rvNext.setHasFixedSize(true)
        rvNext.adapter = nextAdapter

        if (idLeague != null) {
            viewModel.getNextMatch(idLeague).observe(this, Observer {
                if (it != null) {
                    if (it.events != null) {
                        for (item in it.events) {
                            viewModel.getHomeTeam(item.idHomeTeam)
                                .observe(this, Observer { iconHome ->
                                    viewModel.getAwayTeam(item.idAwayTeam)
                                        .observe(this, Observer { iconAway ->
                                            listNextMatch.add(
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

                                            nextAdapter?.setEvent(listNextMatch)
                                            rvNext.visibility = View.VISIBLE
                                            smNextMatch.stopShimmer()
                                            smNextMatch.visibility = View.GONE
                                        })
                                })
                        }
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

    private fun getViewModel(apiRepository: ApiRepository): NextViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NextViewModel(apiRepository) as T
            }

        })[NextViewModel::class.java]
    }

}
