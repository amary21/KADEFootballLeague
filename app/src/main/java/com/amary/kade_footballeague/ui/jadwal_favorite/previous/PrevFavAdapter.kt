package com.amary.kade_footballeague.ui.jadwal_favorite.previous

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.local.model.PrevMatch
import com.amary.kade_footballeague.data.rest.ID_EVENT
import com.amary.kade_footballeague.ui.detail_jadwal.DetailJadwalActivity
import com.amary.kade_footballeague.utils.DateConvert
import com.amary.kade_footballeague.utils.GlideApp
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_jadwal.view.*

class PrevFavAdapter(val  context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var event: MutableList<PrevMatch> = ArrayList()

    fun setEvent(events: List<PrevMatch>) {
        this.event = events as MutableList<PrevMatch>
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): PrevMatch {
        return event[position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_jadwal, parent, false)
        return PrevFavViewHolder(view)
    }


    override fun getItemCount(): Int {
        return event.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PrevFavViewHolder).bind(getItem(position), context)

    }


    class PrevFavViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: PrevMatch, context: Context) {
            val dateConvert = DateConvert()
            itemView.tvEventLeague.text = item.strEvent
            itemView.tvDateEvent.text = dateConvert.convertDate(item.dateEvent)
            itemView.tvTimeEvent.text = dateConvert.convertTime(item.strTime)
            itemView.tvHomeNameTeam.text = item.strHomeTeam
            itemView.tvAwayNameTeam.text = item.strAwayTeam
            itemView.tvScoreHome.text = item.intHomeScore
            itemView.tvScoreAway.text = item.intAwayScore

            itemView.setOnClickListener {
                val intent = Intent(context, DetailJadwalActivity::class.java)
                intent.putExtra(ID_EVENT, item.idEvent)
                context.startActivity(intent)
            }

            GlideApp.with(itemView.context)
                .load(item.imgHomeBadge)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_trophy_home).error(R.drawable.ic_trophy_home))
                .into(itemView.imgHomeListEvent)

            GlideApp.with(itemView.context)
                .load(item.imgAwayBadge)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_trophy_away).error(R.drawable.ic_trophy_away))
                .into(itemView.imgAwayListEvent)

        }

    }
}