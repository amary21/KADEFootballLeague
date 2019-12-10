package com.amary.kade_footballeague.ui.detail_team

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.local.database
import com.amary.kade_footballeague.data.local.model.TeamFav
import com.amary.kade_footballeague.data.rest.ApiClient
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.ID_TEAMS
import com.amary.kade_footballeague.data.rest.ID_TEAMS_SAVES
import com.amary.kade_footballeague.data.rest.response.model.FanArt
import com.amary.kade_footballeague.data.rest.response.model.Teams
import com.amary.kade_footballeague.utils.GlideApp
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.content_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

@Suppress("UNCHECKED_CAST")
class DetailTeamActivity : AppCompatActivity() {


    private val fanArt: ArrayList<FanArt>? = ArrayList()

    private var id : String? = null

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: DetailTeamViewModel

    private var fanArtAdater : DetailTeamAdapter? = null


    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        id = if (savedInstanceState == null){
            intent.getStringExtra(ID_TEAMS)
        }else{
            savedInstanceState.getString(ID_TEAMS_SAVES)
        }

        apiRepository = ApiRepository(apiService)
        fanArtAdater = DetailTeamAdapter(this)
        viewModel = getViewModel(apiRepository)

        vpFanArtTeam.adapter = fanArtAdater
        dotsIndicatorTeam.setViewPager(vpFanArtTeam)
        vpFanArtTeam.adapter?.registerDataSetObserver(dotsIndicatorTeam.dataSetObserver)

        viewModel.getDetailTeam(id!!).observe(this, Observer {
            if (it != null){
                for (i in it.teams){
                    initDetail(i)
                    if (i.strTeamFanart1 != null || i.strTeamFanart2 != null || i.strTeamFanart3 != null || i.strTeamFanart4 != null){
                        initFanArt(i.strTeamFanart1, i.strTeamFanart2, i.strTeamFanart3, i.strTeamFanart4)
                    }
                }
            }
        })

        viewModel.statusNetwork().observe(this, Observer {
            if (!it!!){
                Toast.makeText(this, "Connection error or data not found", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initDetail(item: Teams) {
        GlideApp.with(this)
            .load(item.strTeamBadge)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_football).error(R.drawable.ic_football))
            .into(imgBadgeTeamDetail)

        GlideApp.with(this)
            .load(item.strTeamJersey)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_clothes).error(R.drawable.ic_clothes))
            .into(imgJerseyDetail)

        GlideApp.with(this)
            .load(item.strStadiumThumb)
            .apply(RequestOptions.placeholderOf(R.drawable.image_placeholder).error(R.drawable.image_placeholder))
            .into(imgStadiumDetail)

        tvStadiumDetail.text = item.strStadium
        tvDesDetail.text = item.strDescriptionEN

        favoriteState(item.idTeam)

        if (isFavorite){
            fab_FavoriteTeam.setImageResource(R.drawable.ic_bookmark)
        }else{
            fab_FavoriteTeam.setImageResource(R.drawable.ic_bookmark_not)
        }

        fab_FavoriteTeam.setOnClickListener {
            isFavorite = if (isFavorite){
                removeFromFavorite(item.idTeam, it)
                false
            }else{
                addToFavorite(item, it)
                true
            }
        }
    }

    private fun favoriteState(idTeam: String?) {
        try {
            if (idTeam != null){
                database.use {
                    val result = select(TeamFav.TABLE_TEAM)
                        .whereArgs(
                            "(ID_TEAM = {idTeam})",
                            "idTeam" to idTeam
                        )
                    val favorite = result.parseList(classParser<TeamFav>())
                    if (favorite.isNotEmpty()) isFavorite = true
                }
            }
        } catch (e: SQLiteConstraintException) {
            Log.e("ERROR SQL", e.message.toString())
        }
    }

    private fun addToFavorite(item: Teams, it: View) {
        try {
            database.use {
                insert(TeamFav.TABLE_TEAM,
                    TeamFav.ID_TEAM to item.idTeam,
                    TeamFav.STR_TEAM to item.strTeam,
                    TeamFav.STR_TEAM_BADGE to item.strTeamBadge)
            }


            fab_FavoriteTeam.setImageResource(R.drawable.ic_bookmark)
            Snackbar.make(it, "this Team is a favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Log.e("ERROR SQL", e.message.toString())
        }
    }

    private fun removeFromFavorite(idTeam: String, it: View) {
        try{
            database.use {
                delete(
                    TeamFav.TABLE_TEAM,
                    "(ID_TEAM = {idTeam})",
                    "idTeam" to idTeam
                )
            }
            fab_FavoriteTeam.setImageResource(R.drawable.ic_bookmark_not)
            Snackbar.make(it, "this team is not a favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Log.e("ERROR SQL", e.message.toString())
        }
    }

    private fun initFanArt(
        strFanart1: String?,
        strFanart2: String?,
        strFanart3: String?,
        strFanart4: String?
    ) {
        fanArt?.add(FanArt(strFanart1!!))
        fanArt?.add(FanArt(strFanart2!!))
        fanArt?.add(FanArt(strFanart3!!))
        fanArt?.add(FanArt(strFanart4!!))

        if (fanArt != null) {
            for (i in fanArt){
                Log.e("FANART", i.urlImage)
            }

            fanArtAdater?.setTeams(fanArt)
        }
    }

    private fun getViewModel(apiRepository: ApiRepository): DetailTeamViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DetailTeamViewModel(apiRepository) as T
            }

        })[DetailTeamViewModel::class.java]
    }
}
