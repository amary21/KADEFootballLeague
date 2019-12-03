package com.amary.kade_footballeague.ui.detail_schedule

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
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
import com.amary.kade_footballeague.data.local.database
import com.amary.kade_footballeague.data.local.model.NextMatch
import com.amary.kade_footballeague.data.local.model.PrevMatch
import com.amary.kade_footballeague.data.rest.*
import com.amary.kade_footballeague.data.rest.response.model.Events
import com.amary.kade_footballeague.utils.DateConvert
import com.amary.kade_footballeague.utils.GlideApp
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_schedule.*
import kotlinx.android.synthetic.main.content_detail_schedule.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

@Suppress("UNCHECKED_CAST")
class DetailScheduleActivity : AppCompatActivity() {

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: DetailScheduleViewModel
    private var idEvent: String? = null
    private var homeBadge: String? = null
    private var awayBadge: String? = null
    private var isFavorite: Boolean = false
    private var isNextMatch: Boolean = false
    private var isPrevMatch: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_schedule)

        wbHighLight.setBackgroundColor(Color.BLACK)

        apiRepository = ApiRepository(apiService)
        viewModel = getViewModel(apiRepository)

        idEvent = intent.getStringExtra(ID_EVENT)
        isNextMatch = intent.getBooleanExtra(IS_NEXT, false)
        isPrevMatch = intent.getBooleanExtra(IS_PREV, false)

        viewModel.getLeagueDetail(idEvent).observe(this, Observer {
            if (it != null) {
                isNotEmptyData()
                for (i in it.events!!) {
                    initLogo(i.idHomeTeam, i.idAwayTeam)
                    initData(i)
                    if (i.strHomeLineupGoalkeeper.isNotEmpty()) {
                        initDetail(i)
                    }
                }
            }

        })

        viewModel.statusNetwork().observe(this, Observer {
            if (!it!!) {
                Toast.makeText(this, "Connection error or data not found", Toast.LENGTH_SHORT)
                    .show()
            }
        })


    }

    @SuppressLint("RestrictedApi")
    private fun isNotEmptyData() {
        smDetailMatch.stopShimmer()
        smContentDetailMatch.stopShimmer()
        smDetailMatch.visibility = View.GONE
        smContentDetailMatch.visibility = View.GONE
        nsDetailMatch.visibility = View.VISIBLE
        ly_contentDetail.visibility = View.VISIBLE
        fab_Favorite.visibility = View.VISIBLE

    }

    private fun initLogo(idHomeTeam: String, idAwayTeam: String) {
        viewModel.getHomeTeam(idHomeTeam).observe(this, Observer {
            if (it != null) {
                for (i in it.teams) {
                    homeBadge = i.strTeamBadge
                    GlideApp.with(this)
                        .load(i.strTeamBadge)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_trophy_home).error(R.drawable.ic_trophy_home))
                        .into(imgHomeEventDet)
                }
            }
        })
        viewModel.getAwayTeam(idAwayTeam).observe(this, Observer {
            if (it != null) {
                for (i in it.teams) {
                    awayBadge = i.strTeamBadge
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

        if (item.intAwayScore != null && item.intHomeScore != null) {
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

        favoriteState(item.idEvent, isNextMatch, isPrevMatch)

        if (isFavorite){
            fab_Favorite.setImageResource(R.drawable.ic_bookmark)
        }else{
            fab_Favorite.setImageResource(R.drawable.ic_bookmark_not)
        }

        fab_Favorite.setOnClickListener {
            isFavorite = if (isFavorite) {
                removeFromeFavorite(item.idEvent, isNextMatch, isPrevMatch, it)
                false
            } else {
                addToFavorite(item, isNextMatch, isPrevMatch, it)
                true
            }
        }
    }


    private fun favoriteState(
        id: String?,
        nextMatch: Boolean,
        prevMatch: Boolean
    ) {
        try {
            if (id != null) {
                if (nextMatch && !prevMatch) {
                    database.use {
                        val result = select(NextMatch.TABLE_NEXT)
                            .whereArgs(
                                "(ID_EVENT = {idEvent})",
                                "idEvent" to id
                            )
                        val favorite = result.parseList(classParser<NextMatch>())
                        if (favorite.isNotEmpty()) isFavorite = true
                    }
                } else {

                    database.use {
                        val result = select(PrevMatch.TABLE_PREV)
                            .whereArgs(
                                "(ID_EVENT = {idEvent})",
                                "idEvent" to id
                            )
                        val favorite = result.parseList(classParser<PrevMatch>())
                        if (favorite.isNotEmpty()) isFavorite = true
                    }
                }
            }
        } catch (e: SQLiteConstraintException) {
            Log.e("ERROR SQL", e.message.toString())
        }
    }

    private fun removeFromeFavorite(
        id: String?,
        nextMatch: Boolean,
        prevMatch: Boolean,
        it: View
    ) {
        try {
            if (id != null) {
                if (nextMatch && !prevMatch) {
                    database.use {
                        delete(
                            NextMatch.TABLE_NEXT,
                            "(ID_EVENT = {idEvent})",
                            "idEvent" to id
                        )
                    }
                } else {
                    database.use {
                        delete(
                            PrevMatch.TABLE_PREV,
                            "(ID_EVENT = {idEvent})",
                            "idEvent" to id
                        )
                    }
                }
            }

            fab_Favorite.setImageResource(R.drawable.ic_bookmark_not)
            Snackbar.make(it, "this event is not a favorite", Snackbar.LENGTH_SHORT).show()

        } catch (e: SQLiteConstraintException) {
            Log.e("ERROR SQL", e.message.toString())
        }
    }

    private fun addToFavorite(
        item: Events,
        nextMatch: Boolean,
        prevMatch: Boolean,
        it: View
    ) {
        try {
            if (nextMatch && !prevMatch) {
                database.use {
                    insert(
                        NextMatch.TABLE_NEXT,
                        NextMatch.ID_EVENT to item.idEvent,
                        NextMatch.STR_EVENT to item.strEvent,
                        NextMatch.STR_HOME_TEAM to item.strHomeTeam,
                        NextMatch.STR_AWAY_TEAM to item.strAwayTeam,
                        NextMatch.DATE_EVENT to item.dateEvent,
                        NextMatch.STR_TIME to item.strTime,
                        NextMatch.IMG_HOME_BADGE to homeBadge,
                        NextMatch.IMG_AWAY_BADGE to awayBadge
                    )
                }
            } else {
                database.use {
                    insert(
                        PrevMatch.TABLE_PREV,
                        PrevMatch.ID_EVENT to item.idEvent,
                        PrevMatch.STR_EVENT to item.strEvent,
                        PrevMatch.STR_HOME_TEAM to item.strHomeTeam,
                        PrevMatch.STR_AWAY_TEAM to item.strAwayTeam,
                        PrevMatch.INT_HOME_SCORE to item.intHomeScore,
                        PrevMatch.INT_AWAY_SCORE to item.intAwayScore,
                        PrevMatch.DATE_EVENT to item.dateEvent,
                        PrevMatch.STR_TIME to item.strTime,
                        PrevMatch.IMG_HOME_BADGE to homeBadge,
                        PrevMatch.IMG_AWAY_BADGE to awayBadge
                    )
                }
            }

            fab_Favorite.setImageResource(R.drawable.ic_bookmark)
            Snackbar.make(it, "this event is a favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Log.e("ERROR SQL", e.message.toString())
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
