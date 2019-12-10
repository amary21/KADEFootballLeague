package com.amary.kade_footballeague.ui.detail_league.team

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.rest.ID_TEAMS
import com.amary.kade_footballeague.data.rest.response.model.Teams
import com.amary.kade_footballeague.ui.detail_team.DetailTeamActivity
import com.amary.kade_footballeague.utils.GlideApp
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_teams.view.*

class TeamsAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var team: MutableList<Teams> = ArrayList()

    fun setTeam(team: List<Teams>) {
        this.team = team as MutableList<Teams>
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): Teams {
        return team[position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teams, parent, false)
        return TeamViewHolder(view)
    }


    override fun getItemCount(): Int {
        return team.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TeamViewHolder).bind(getItem(position), context)
    }


    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Teams, context: Context) {
            itemView.tvTeam.text = item.strTeam

            GlideApp.with(itemView.context)
                .load(item.strTeamBadge)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_football).error(R.drawable.ic_football))
                .into(itemView.imgBadgeTeam)

            itemView.setOnClickListener {
                val intent = Intent(context, DetailTeamActivity::class.java)
                intent.putExtra(ID_TEAMS, item.idTeam)
                context.startActivity(intent)
            }
        }

    }
}