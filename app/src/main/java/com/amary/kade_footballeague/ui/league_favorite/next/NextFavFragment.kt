package com.amary.kade_footballeague.ui.league_favorite.next

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.local.database
import com.amary.kade_footballeague.data.local.model.NextMatch
import kotlinx.android.synthetic.main.fragment_next_fav.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class NextFavFragment : Fragment() {

    private var nextAdapter : NextFavAdapter? = null
    private var favorites = ArrayList<NextMatch>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_next_fav, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        nextAdapter = NextFavAdapter(context as Activity)
        rvFavNext.layoutManager = LinearLayoutManager(context)
        rvFavNext.setHasFixedSize(true)
        rvFavNext.adapter = nextAdapter

        showFavorite()
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            val result = select(NextMatch.TABLE_NEXT)
            val favorite = result.parseList(classParser<NextMatch>())
            nextAdapter?.setEvent(favorite)
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

}
