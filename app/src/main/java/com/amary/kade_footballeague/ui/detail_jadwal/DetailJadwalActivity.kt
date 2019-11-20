package com.amary.kade_footballeague.ui.detail_jadwal

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.rest.ApiClient
import com.amary.kade_footballeague.rest.ApiRepository
import com.amary.kade_footballeague.rest.ID_EVENT
import com.amary.kade_footballeague.rest.ID_LEAGUE
import com.amary.kade_footballeague.rest.response.model.Events
import com.amary.kade_footballeague.utils.GlideApp
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_jadwal.*
import kotlinx.android.synthetic.main.content_detail_jadwal.*

class DetailJadwalActivity : AppCompatActivity() {

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: DetailJadwalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_jadwal)
//        setSupportActionBar(toolbar)
        wbHighLight.setBackgroundColor(Color.BLACK)

        apiRepository = ApiRepository(apiService)
        viewModel = getViewModel(apiRepository)

        val id = intent.getStringExtra(ID_EVENT)
        Log.e("ID_EVENT", id!!)

        viewModel.getLeagueDetail(id).observe(this, Observer {
            if (it != null){
                smDetailMatch.visibility = View.GONE
                nsDetailMatch.visibility = View.VISIBLE
                for (i in it.events){
                    initData(i)
                    initDetail(i)
                    initLogo(i.idHomeTeam, i.idAwayTeam)
                }
            }

        })


    }

    private fun initLogo(idHomeTeam: String, idAwayTeam: String) {
        viewModel.getHomeTeam(idHomeTeam).observe(this, Observer {
            if (it != null){
                for (i in it.teams){
                    GlideApp.with(this)
                        .load(i.strTeamBadge)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_trophy_home).error(R.drawable.ic_trophy_home))
                        .into(imgHomeEventDet)
                }
            }
        })
        viewModel.getAwayTeam(idAwayTeam).observe(this, Observer {
            if (it != null){
                for (i in it.teams){
                    GlideApp.with(this)
                        .load(i.strTeamBadge)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_trophy_away).error(R.drawable.ic_trophy_away))
                        .into(imgAwayEventDet)
                }
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initDetail(item: Events) {
        val homeYellowCard = item.strHomeYellowCards.split(";").map { it.trim() }
        val awayYellowCard = item.strAwayYellowCards.split(";").map { it.trim() }
        val homeRedCard = item.strHomeRedCards.split(";").map { it.trim() }
        val awayRedCard = item.strAwayRedCards.split(";").map { it.trim() }

        homeYellowCard.forEach {
            Log.e("HOME CARD", it)
        }

        awayYellowCard.forEach {
            Log.e("AWAY CARD", it)
        }

        tvYellowHome.text = (homeYellowCard.size - 1).toString()
        tvYellowAway.text = (awayYellowCard.size - 1).toString()
        tvRedHome.text = (homeRedCard.size - 1).toString()
        tvRedAway.text = (awayRedCard.size - 1).toString()

        tvShotHome.text = item.intHomeScore ?: "0"
        tvShotAway.text = item.intAwayScore ?: "0"

        val oldValue = "watch?v="
        val newValue = "embed/"
        wbHighLight.settings.javaScriptEnabled = true
        wbHighLight.webChromeClient = WebChromeClient()
        wbHighLight.loadUrl(item.strVideo.replace(oldValue, newValue))



    }

    private fun initData(item: Events) {
        tvEventLeagueDet.text = item.strEvent
        tvDateEventDet.text = item.dateEvent
        tvHomeNameTeamDet.text = item.strHomeTeam
        tvAwayNameTeamDet.text = item.strAwayTeam
        if (item.intAwayScore != null && item.intHomeScore != null){
            tvScoreHomeDet.text = item.intHomeScore
            tvScoreAwayDet.text = item.intAwayScore
        }
    }

    private fun getViewModel(apiRepository: ApiRepository): DetailJadwalViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DetailJadwalViewModel(apiRepository) as T
            }

        })[DetailJadwalViewModel::class.java]
    }
}
