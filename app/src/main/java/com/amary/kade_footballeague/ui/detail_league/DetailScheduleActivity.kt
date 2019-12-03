package com.amary.kade_footballeague.ui.detail_league

import android.content.Intent
import android.net.Uri
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
import com.amary.kade_footballeague.data.rest.ApiClient
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.ID_LEAGUE
import com.amary.kade_footballeague.data.rest.NAME_LEAGUE
import com.amary.kade_footballeague.data.rest.response.model.FanArt
import com.amary.kade_footballeague.data.rest.response.model.LeagueDet
import com.amary.kade_footballeague.ui.schedule_list.ScheduleActivity
import com.amary.kade_footballeague.utils.GlideApp
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_league.*
import kotlinx.android.synthetic.main.content_detail_league.*

@Suppress("UNCHECKED_CAST")
class DetailScheduleActivity : AppCompatActivity() {

    private val fanArt: ArrayList<FanArt>? = ArrayList()

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: DetailScheduleViewModel

    private var fanArtAdater : DetailScheduleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)

        val id = intent.getStringExtra(ID_LEAGUE)

        apiRepository = ApiRepository(apiService)
        fanArtAdater = DetailScheduleAdapter(this)
        viewModel = getViewModel(apiRepository)

        fanArtAdater = DetailScheduleAdapter(this)
        vpFanArt.adapter = fanArtAdater

        viewModel.getLeagueDetail(id!!).observe(this, Observer {
            if (it != null){
                for (i in it.leagues){
                    initDetail(i)
                    if (i.strFanart1 != null || i.strFanart2 != null || i.strFanart3 != null || i.strFanart4 != null){
                        initFanArt(i.strFanart1, i.strFanart2, i.strFanart3, i.strFanart4)
                    }
                }
            }else{
                smDetaiLiga.visibility = View.VISIBLE
                smDetaiLiga.startShimmer()
            }
        })

        viewModel.statusNetwork().observe(this, Observer {
            if (!it!!){
                Toast.makeText(this, "Connection error or data not found", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initDetail(item: LeagueDet) {
        smDetaiLiga.stopShimmer()
        smDetaiLiga.visibility = View.GONE
        imgBadgeDetail.visibility = View.VISIBLE
        tvTitleLeague.visibility = View.VISIBLE
        tvCountryLeague.visibility = View.VISIBLE
        tvGenderLeague.visibility = View.VISIBLE
        tvSportLeague.visibility = View.VISIBLE
        tvWebsiteLeague.visibility = View.VISIBLE
        viewline_1.visibility = View.VISIBLE
        viewline_2.visibility = View.VISIBLE
        tvDescriptionLeague.visibility = View.VISIBLE
        textDescription.visibility = View.VISIBLE
        btnCheckSchedule.visibility = View.VISIBLE

        GlideApp.with(this)
            .load(item.strBadge)
            .apply(RequestOptions.placeholderOf(R.drawable.image_placeholder).error(R.drawable.image_placeholder))
            .into(imgBadgeDetail)

        tvTitleLeague.text = item.strLeague
        tvCountryLeague.text = item.strCountry
        tvGenderLeague.text = item.strGender
        tvSportLeague.text = item.strSport
        tvWebsiteLeague.text = item.strWebsite

        tvDescriptionLeague.text = item.strDescriptionEN

        if (tvWebsiteLeague != null){
            tvWebsiteLeague.setOnClickListener{
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://"+item.strWebsite))
                startActivity(Intent.createChooser(intent, "Browse with"))
            }
        }

        goFacebook(item.strFacebook)
        goTwitter(item.strTwitter)
        goYoutube(item.strYoutube)

        btnCheckSchedule.setOnClickListener {
            val intent = Intent(this, ScheduleActivity::class.java)
            intent.putExtra(ID_LEAGUE, item.idLeague)
            intent.putExtra(NAME_LEAGUE, item.strLeague)
            startActivity(intent)
        }
    }

    private fun goYoutube(strYoutube: String) {
        if (strYoutube.isNotEmpty()){
            imgYoutube.visibility = View.VISIBLE
            imgYoutube.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://$strYoutube"))
                startActivity(Intent.createChooser(intent, "Browse with"))
            }
        }else{
            imgYoutube.visibility = View.GONE
        }
    }

    private fun goTwitter(strTwitter: String) {
        if (strTwitter.isNotEmpty()){
            imgTwitter.visibility = View.VISIBLE
            imgTwitter.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://$strTwitter"))
                startActivity(Intent.createChooser(intent, "Browse with"))
            }
        }else{
            imgTwitter.visibility = View.GONE
        }
    }

    private fun goFacebook(strFacebook: String) {
        if (strFacebook.isNotEmpty()){
            imgFacebook.visibility = View.VISIBLE
            imgFacebook.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://$strFacebook"))
                startActivity(Intent.createChooser(intent, "Browse with"))
            }
        }else{
            imgFacebook.visibility = View.GONE
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

            fanArtAdater?.setLeagues(fanArt)
        }
    }

    private fun getViewModel(apiRepository: ApiRepository): DetailScheduleViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DetailScheduleViewModel(apiRepository) as T
            }

        })[DetailScheduleViewModel::class.java]
    }
}
