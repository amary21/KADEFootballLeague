package com.amary.kade_footballeague.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.rest.ID_LEAGUE
import com.amary.kade_footballeague.data.rest.response.model.ListLeagues
import com.amary.kade_footballeague.ui.detail_league.DetailLeagueActivity
import com.amary.kade_footballeague.utils.GlideApp
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_league.view.*

class MainAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var league: MutableList<ListLeagues> = ArrayList()

    fun setLeagues(leagues: List<ListLeagues>) {
        this.league = leagues as MutableList<ListLeagues>
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): ListLeagues {
        return league[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_league, parent, false)
        return LeaguesItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return league.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as LeaguesItemViewHolder).bind(getItem(position), context)
        }


    class LeaguesItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ListLeagues, context: Context) {
            if (item.strSport == "Soccer") {
                itemView.tvLeague.text = item.strLeague
                itemView.setOnClickListener {
                    val intent = Intent(context, DetailLeagueActivity::class.java)
                    intent.putExtra(ID_LEAGUE, item.idLeague)
                    context.startActivity(intent)
                }
                GlideApp.with(itemView.context)
                    .load(item.imgBadgeLeagues)
                    .apply(RequestOptions.placeholderOf(R.drawable.image_placeholder).error(R.drawable.image_placeholder))
                    .into(itemView.imgCoverLeague)

            }
        }

    }

}