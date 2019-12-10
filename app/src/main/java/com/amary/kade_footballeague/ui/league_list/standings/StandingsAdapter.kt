package com.amary.kade_footballeague.ui.league_list.standings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.rest.response.model.Table
import com.amary.kade_footballeague.utils.GlideApp
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_standings.view.*

class StandingsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var tables : MutableList<Table> = ArrayList()

    fun setTable(tables: List<Table>) {
        this.tables = tables as MutableList<Table>
        notifyDataSetChanged()
    }

    private fun getItem(position: Int) : Table{
        return tables[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_standings, parent, false)
        return StandViewHolder(view)
    }


    override fun getItemCount(): Int {
        return tables.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as StandViewHolder).bind(getItem(position))
    }
    
    class StandViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Table) {

            if (item.imgBadge != null){
                GlideApp.with(itemView.context)
                    .load(item.imgBadge)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_football).error(R.drawable.ic_football))
                    .into(itemView.imgTableBadge)

            }

            itemView.tvTableNameTeam.text = item.name
            itemView.tvTablePlayed.text = item.played
            itemView.tvTableGoalsfor.text = item.goalsfor
            itemView.tvTableGoalsagainst.text = item.goalsagainst
            itemView.tvTableGoalsdifference.text = item.goalsdifference
            itemView.tvTableWin.text = item.win
            itemView.tvTableLoss.text = item.loss
            itemView.tvTableDraw.text = item.draw
            itemView.tvTableTotal.text = item.total
        }

    }
}