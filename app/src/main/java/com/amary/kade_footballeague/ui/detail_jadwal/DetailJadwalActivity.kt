package com.amary.kade_footballeague.ui.detail_jadwal

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.rest.ApiClient
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.ID_EVENT
import com.amary.kade_footballeague.data.rest.response.model.Events
import com.amary.kade_footballeague.utils.DateConvert
import com.amary.kade_footballeague.utils.GlideApp
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_jadwal.*
import kotlinx.android.synthetic.main.content_detail_jadwal.*

@Suppress("UNCHECKED_CAST")
class DetailJadwalActivity : AppCompatActivity() {

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: DetailJadwalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_jadwal)

        wbHighLight.setBackgroundColor(Color.BLACK)

        apiRepository = ApiRepository(apiService)
        viewModel = getViewModel(apiRepository)

        val id = intent.getStringExtra(ID_EVENT)
        Log.e("ID_EVENT", id!!)

        viewModel.getLeagueDetail(id).observe(this, Observer {
            if (it != null){
                isNotEmptyData()
                for (i in it.events!!){
                    initLogo(i.idHomeTeam, i.idAwayTeam)
                    initData(i)
                    if (i.strHomeLineupGoalkeeper.isNotEmpty()) {
                        initDetail(i)
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

    private fun isNotEmptyData() {
        smDetailMatch.stopShimmer()
        smContentDetailMatch.stopShimmer()
        smDetailMatch.visibility = View.GONE
        smContentDetailMatch.visibility = View.GONE
        nsDetailMatch.visibility = View.VISIBLE
        ly_contentDetail.visibility = View.VISIBLE

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

        tvYellowHome.text = (homeYellowCard.size - 1).toString()
        tvYellowAway.text = (awayYellowCard.size - 1).toString()
        tvRedHome.text = (homeRedCard.size - 1).toString()
        tvRedAway.text = (awayRedCard.size - 1).toString()

        val oldVideo = "watch?v="
        val newVideo = "embed/"
        wbHighLight.settings.javaScriptEnabled = true
        wbHighLight.webChromeClient = WebChromeClient()
        wbHighLight.loadUrl(item.strVideo.replace(oldVideo, newVideo))

        val oldValue = ";"
        val newValue = "\n"

        tvHomeGK.text = item.strHomeLineupGoalkeeper.replace(oldValue, newValue)
        tvAwayGK.text = item.strAwayLineupGoalkeeper.replace(oldValue, newValue)
        tvHomeDF.text = item.strHomeLineupDefense.replace(oldValue, newValue)
        tvAwayDF.text = item.strAwayLineupDefense.replace(oldValue, newValue)
        tvHomeMF.text = item.strHomeLineupMidfield.replace(oldValue, newValue)
        tvAwayMF.text = item.strAwayLineupMidfield.replace(oldValue, newValue)
        tvHomeFW.text = item.strHomeLineupForward.replace(oldValue, newValue)
        tvAwayFW.text = item.strAwayLineupForward.replace(oldValue, newValue)
        tvHomeSub.text = item.strHomeLineupSubstitutes.replace(oldValue, newValue)
        tvAwaySub.text = item.strAwayLineupSubstitutes.replace(oldValue, newValue)
        tvYellowHomeDet.text = item.strHomeYellowCards.replace(oldValue, newValue)
        tvYellowAwayDet.text = item.strAwayYellowCards.replace(oldValue, newValue)
        tvRedHomeDet.text = item.strHomeRedCards.replace(oldValue, newValue)
        tvRedAwayDet.text = item.strAwayRedCards.replace(oldValue, newValue)


        GlideApp.with(this)
            .load(item.strThumb)
            .apply(RequestOptions.placeholderOf(R.drawable.image_placeholder).error(R.drawable.image_placeholder))
            .into(imgPosterMatchDet)
    }

    private fun initData(item: Events) {
        val dateConver = DateConvert()
        tvEventLeagueDet.text = item.strEvent
        tvDateEventDet.text = dateConver.convertDate(item.dateEvent)
        tvTimeEventDet.text = dateConver.convertTime(item.strTime)
        tvHomeNameTeamDet.text = item.strHomeTeam
        tvAwayNameTeamDet.text = item.strAwayTeam
        if (item.intAwayScore != null && item.intHomeScore != null){
            tvScoreHomeDet.text = item.intHomeScore
            tvScoreAwayDet.text = item.intAwayScore
            tvShotHome.text = item.intHomeScore
            tvShotAway.text = item.intAwayScore
            txtStatus.visibility = View.VISIBLE
        } else {
            tvScoreHomeDet.text = "-"
            tvScoreAwayDet.text = "-"
            tvShotHome.text = "0"
            tvShotAway.text = "0"
            txtStatus.visibility = View.GONE
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
