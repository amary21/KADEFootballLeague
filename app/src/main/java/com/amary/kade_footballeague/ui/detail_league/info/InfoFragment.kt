package com.amary.kade_footballeague.ui.detail_league.info

import android.content.Intent
import android.net.Uri
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
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.rest.ApiClient
import com.amary.kade_footballeague.data.rest.ApiRepository
import com.amary.kade_footballeague.data.rest.ID_LEAGUE
import com.amary.kade_footballeague.data.rest.NAME_LEAGUE
import com.amary.kade_footballeague.data.rest.response.model.LeagueDet
import com.amary.kade_footballeague.ui.league_list.LeagueListActivity
import com.amary.kade_footballeague.utils.GlideApp
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_info.*

@Suppress("UNCHECKED_CAST")
class InfoFragment : Fragment() {

    private var id: String? = null

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: InfoViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = this.arguments
        id = bundle?.getString(ID_LEAGUE)

        apiRepository = ApiRepository(apiService)
        viewModel = getViewModel(apiRepository)

        viewModel.getLeagueDetail(id!!).observe(this, Observer {
            if (it != null) {
                initDetail(it.leagues[0])
            } else {
                smDetaiLeague.visibility = View.VISIBLE
                smDetaiLeague.startShimmer()
            }
        })

        viewModel.statusNetwork().observe(this, Observer {
            if (!it!!) {
                Toast.makeText(context, "Connection error or data not found", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }

    private fun initDetail(item: LeagueDet) {
        smDetaiLeague.stopShimmer()
        smDetaiLeague.visibility = View.GONE
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

        if (tvWebsiteLeague != null) {
            tvWebsiteLeague.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://" + item.strWebsite))
                startActivity(Intent.createChooser(intent, "Browse with"))
            }
        }

        goFacebook(item.strFacebook)
        goTwitter(item.strTwitter)
        goYoutube(item.strYoutube)

        btnCheckSchedule.setOnClickListener {
            val intent = Intent(context, LeagueListActivity::class.java)
            intent.putExtra(ID_LEAGUE, item.idLeague)
            intent.putExtra(NAME_LEAGUE, item.strLeague)
            startActivity(intent)
        }
    }

    private fun goYoutube(strYoutube: String) {
        if (strYoutube.isNotEmpty()) {
            imgYoutube.visibility = View.VISIBLE
            imgYoutube.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://$strYoutube"))
                startActivity(Intent.createChooser(intent, "Browse with"))
            }
        } else {
            imgYoutube.visibility = View.GONE
        }
    }

    private fun goTwitter(strTwitter: String) {
        if (strTwitter.isNotEmpty()) {
            imgTwitter.visibility = View.VISIBLE
            imgTwitter.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://$strTwitter"))
                startActivity(Intent.createChooser(intent, "Browse with"))
            }
        } else {
            imgTwitter.visibility = View.GONE
        }
    }

    private fun goFacebook(strFacebook: String) {
        if (strFacebook.isNotEmpty()) {
            imgFacebook.visibility = View.VISIBLE
            imgFacebook.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://$strFacebook"))
                startActivity(Intent.createChooser(intent, "Browse with"))
            }
        } else {
            imgFacebook.visibility = View.GONE
        }
    }

    private fun getViewModel(apiRepository: ApiRepository): InfoViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return InfoViewModel(apiRepository) as T
            }

        })[InfoViewModel::class.java]
    }

}
