package com.amary.kade_footballeague.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.rest.ID_LEAGUE
import com.amary.kade_footballeague.rest.response.model.Leagues
import com.amary.kade_footballeague.ui.detail_liga.DetailLigaActivity
import com.amary.kade_footballeague.utils.GlideApp
import kotlinx.android.synthetic.main.item_league.view.*

class MainAdapter (private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var league: MutableList<Leagues> = ArrayList()

    fun setLeagues(leagues: List<Leagues>){
        this.league.addAll(leagues)
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): Leagues {
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
        fun bind(item: Leagues, context: Context) {
            if (item.strSport == "Soccer"){
                itemView.tvLeague.text = item.strLeague
                itemView.setOnClickListener {
                    val intent = Intent(context, DetailLigaActivity::class.java)
                    intent.putExtra(ID_LEAGUE, item.idLeague)
                    context.startActivity(intent)
                }
            }
        }

    }

}