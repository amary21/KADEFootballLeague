package com.amary.kade_footballeague.ui.league_favorite.teams


import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.local.database
import com.amary.kade_footballeague.data.local.model.TeamFav
import kotlinx.android.synthetic.main.fragment_team_fav.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

/**
 * A simple [Fragment] subclass.
 */
class TeamFavFragment : Fragment() {

    private var teamAdapter : TeamFavAdapter? = null
    private var favorites = ArrayList<TeamFav>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_fav, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        teamAdapter = TeamFavAdapter(context as Activity)
        rvTeamFav.layoutManager = GridLayoutManager(context, 2)
        rvTeamFav.setHasFixedSize(true)
        rvTeamFav.adapter = teamAdapter

        showFavorite()
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            val result = select(TeamFav.TABLE_TEAM)
            val favorite = result.parseList(classParser<TeamFav>())
            teamAdapter?.setTeam(favorite)
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }


}
