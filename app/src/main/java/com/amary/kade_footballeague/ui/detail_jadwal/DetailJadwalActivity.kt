package com.amary.kade_footballeague.ui.detail_jadwal

import android.os.Bundle
import android.util.Log
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

class DetailJadwalActivity : AppCompatActivity() {

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: DetailJadwalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_jadwal)
//        setSupportActionBar(toolbar)

        apiRepository = ApiRepository(apiService)
        viewModel = getViewModel(apiRepository)

        val id = intent.getStringExtra(ID_EVENT)
        Log.e("ID_EVENT", id!!)

        viewModel.getLeagueDetail(id).observe(this, Observer {
            if (it != null){
                for (i in it.events){
                    initData(i)
                    initDetail(i)
                    initLogo(i.idHomeTeam, i.idAwayTeam)
                }
            }

        })


    }

    private fun initLogo(idHomeTeam: String, idAwayTeam: String) {
//        viewModel.getHomeTeam(idHomeTeam).observe(this, Observer {
//            if (it != null){
//                for (i in it.teams){
//                    GlideApp.with(this)
//                        .load(i.strTeamBadge)
//                            .apply(RequestOptions.placeholderOf(R.drawable.ic_trophy_home).error(R.drawable.ic_trophy_home))
//                            .into(imgHomeEventDet)
//                }
//            }
//        })
//        viewModel.getAwayTeam(idAwayTeam).observe(this, Observer {
//            if (it != null){
//                for (i in it.teams){
//                    GlideApp.with(this)
//                        .load(i.strTeamBadge)
//                        .apply(RequestOptions.placeholderOf(R.drawable.ic_trophy_away).error(R.drawable.ic_trophy_away))
//                        .into(imgAwayEventDet)
//                }
//            }
//        })
    }

    private fun initDetail(item: Events) {

    }

    private fun initData(item: Events) {
//        tvEventLeagueDet.text = item.strEvent
//        tvDateEventDet.text = item.dateEvent
//        tvHomeNameTeamDet.text = item.strHomeTeam
//        tvAwayNameTeamDet.text = item.strAwayTeam
//        if (item.intAwayScore != null && item.intHomeScore != null){
//            tvScoreHomeDet.text = item.intHomeScore
//            tvScoreAwayDet.text = item.intAwayScore
//        }
    }

    private fun getViewModel(apiRepository: ApiRepository): DetailJadwalViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DetailJadwalViewModel(apiRepository) as T
            }

        })[DetailJadwalViewModel::class.java]
    }
}
