package com.amary.kade_footballeague.ui.detail_league.team

import android.app.Activity
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
import androidx.recyclerview.widget.GridLayoutManager

import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.rest.ApiClient
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.ID_LEAGUE
import kotlinx.android.synthetic.main.fragment_team.*

@Suppress("UNCHECKED_CAST")
class TeamFragment : Fragment() {

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: TeamViewModel
    private var teamsAdapter : TeamsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = this.arguments
        val idLeague = bundle?.getString(ID_LEAGUE)

        apiRepository = ApiRepository(apiService)
        viewModel = getViewModel(apiRepository)
        teamsAdapter = TeamsAdapter(context as Activity)


        rvTeam.layoutManager = GridLayoutManager(context, 2)
        rvTeam.setHasFixedSize(true)
        rvTeam.adapter = teamsAdapter


        if (idLeague != null){
            viewModel.getTeamAll(idLeague).observe(this, Observer {
                if (it != null){
                    teamsAdapter?.setTeam(it.teams)
                    rvTeam.visibility = View.VISIBLE
                    smTeams.stopShimmer()
                    smTeams.visibility = View.GONE
                }
            })
        }

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

    private fun getViewModel(apiRepository: ApiRepository): TeamViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return TeamViewModel(apiRepository) as T
            }

        })[TeamViewModel::class.java]
    }

}
