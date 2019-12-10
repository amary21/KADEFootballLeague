package com.amary.kade_footballeague.ui.league_list.standings

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.rest.ApiClient
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.ID_LEAGUE
import com.amary.kade_footballeague.data.rest.response.model.Table
import kotlinx.android.synthetic.main.fragment_standings.*

@Suppress("UNCHECKED_CAST")
class StandingsFragment : Fragment() {

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: StandingsViewModel
    private var standingsAdapter : StandingsAdapter? = null
    private var listTable = ArrayList<Table>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_standings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = this.arguments
        val idLeagues = bundle?.getString(ID_LEAGUE)

        apiRepository = ApiRepository(apiService)
        viewModel = getViewModel(apiRepository)
        standingsAdapter = StandingsAdapter()

        rvStandings.layoutManager = LinearLayoutManager(context)
        rvStandings.setHasFixedSize(true)
        rvStandings.addItemDecoration(DividerItemDecoration(rvStandings.context, DividerItemDecoration.VERTICAL))
        rvStandings.adapter = standingsAdapter


        if (idLeagues != null){
            getStanding(idLeagues)
        }

        getNework()
    }

    private fun getStanding(idLeagues : String){
        listTable.clear()
        viewModel.getStandingLeague(idLeagues).observe(this, Observer {
            if (it.table != null){
                for(item in it.table){
                    viewModel.getBadgeTeam(item.teamid).observe(this, Observer { icon->
                        listTable.add(
                            Table(
                                item.name,
                                item.teamid,
                                item.played,
                                item.goalsfor,
                                item.goalsagainst,
                                item.goalsdifference,
                                item.win,
                                item.draw,
                                item.loss,
                                item.total,
                                icon.teams[0].strTeamBadge
                            )
                        )

                        val sortedList =
                            listTable.sortedWith(compareBy(
                                {p->p.total},
                                {w->w.win},
                                {d->d.draw},
                                {l->l.loss},
                                {gd->gd.goalsdifference}
                            )).reversed()

                        standingsAdapter?.setTable(sortedList)
                        rvStandings.visibility = View.VISIBLE
                        smStanding.stopShimmer()
                        smStanding.visibility = View.GONE
                    })
                }
            }
        })
    }

    private fun getNework(){
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

    private fun getViewModel(apiRepository: ApiRepository): StandingsViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return StandingsViewModel(apiRepository) as T
            }
        })[StandingsViewModel::class.java]
    }

}
