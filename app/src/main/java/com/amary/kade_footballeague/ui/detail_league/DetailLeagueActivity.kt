package com.amary.kade_footballeague.ui.detail_league

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.rest.ApiClient
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.ID_LEAGUE
import com.amary.kade_footballeague.data.rest.ID_LEAGUE_SAVE
import com.amary.kade_footballeague.data.rest.response.model.FanArt
import com.amary.kade_footballeague.ui.detail_league.info.InfoFragment
import com.amary.kade_footballeague.ui.detail_league.team.TeamFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_detail_league.*

@Suppress("UNCHECKED_CAST")
class DetailLeagueActivity : AppCompatActivity() {

    private val fanArt: ArrayList<FanArt>? = ArrayList()

    private var id : String? = null

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: DetailLeagueViewModel

    private var fanArtAdater : DetailLeagueAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)

        id = if (savedInstanceState == null){
            intent.getStringExtra(ID_LEAGUE)
        }else{
            savedInstanceState.getString(ID_LEAGUE_SAVE)
        }

        setupViewPager(detailLeagueContainer, id)

        apiRepository = ApiRepository(apiService)
        fanArtAdater = DetailLeagueAdapter(this)
        viewModel = getViewModel(apiRepository)

        vpFanArt.adapter = fanArtAdater
        dotsIndicator.setViewPager(vpFanArt)
        vpFanArt.adapter?.registerDataSetObserver(dotsIndicator.dataSetObserver)

        viewModel.getLeagueDetail(id!!).observe(this, Observer {
            if (it != null){
                for (i in it.leagues){
                    if (i.strFanart1 != null || i.strFanart2 != null || i.strFanart3 != null || i.strFanart4 != null){
                        initFanArt(i.strFanart1, i.strFanart2, i.strFanart3, i.strFanart4)
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

    private fun setupViewPager(viewPager: ViewPager, id: String?) {
        val adapter = DetailLeagueTabAdapter(supportFragmentManager)
        val bundle = Bundle()
        bundle.putString(ID_LEAGUE, id)

        val fragInfo = InfoFragment()
        val fragTeam = TeamFragment()

        fragInfo.arguments = bundle
        fragTeam.arguments = bundle

        adapter.addFrag(fragInfo)
        adapter.addFrag(fragTeam)

        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabMenuDetailLeague))
        tabMenuDetailLeague.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0 != null) {
                    viewPager.currentItem = p0.position
                }
            }

        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(ID_LEAGUE_SAVE, id)
        super.onSaveInstanceState(outState)
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

    private fun getViewModel(apiRepository: ApiRepository): DetailLeagueViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DetailLeagueViewModel(apiRepository) as T
            }

        })[DetailLeagueViewModel::class.java]
    }
}
