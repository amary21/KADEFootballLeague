package com.amary.kade_footballeague.ui.league_favorite.previous

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.local.database
import com.amary.kade_footballeague.data.local.model.PrevMatch
import kotlinx.android.synthetic.main.fragment_prev_fav.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class PrevFavFragment : Fragment() {

    private var prevAdapter : PrevFavAdapter? = null
    private var favorites = ArrayList<PrevMatch>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_prev_fav, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prevAdapter = PrevFavAdapter(context as Activity)
        rvFavPrevious.layoutManager = LinearLayoutManager(context)
        rvFavPrevious.setHasFixedSize(true)
        rvFavPrevious.adapter = prevAdapter

        showFavorite()
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            val result = select(PrevMatch.TABLE_PREV)
            val favorite = result.parseList(classParser<PrevMatch>())
            prevAdapter?.setEvent(favorite)
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

}
