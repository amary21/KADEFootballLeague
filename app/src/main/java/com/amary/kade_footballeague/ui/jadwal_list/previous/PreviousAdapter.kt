package com.amary.kade_footballeague.ui.jadwal_list.previous

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.rest.ID_EVENT
import com.amary.kade_footballeague.rest.response.model.Events
import com.amary.kade_footballeague.ui.detail_jadwal.DetailJadwalActivity
import com.amary.kade_footballeague.ui.jadwal_list.next.NextAdapter
import kotlinx.android.synthetic.main.item_jadwal.view.*

class PreviousAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var event : MutableList<Events> = ArrayList()

    fun setEvent(events: List<Events>){
        this.event.addAll(events)
        notifyDataSetChanged()
    }

    private fun getItem(position: Int) : Events {
        return event[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_jadwal, parent, false)
        return PrevViewHolder(view)
    }

    override fun getItemCount(): Int {
        return event.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PrevViewHolder).bind(getItem(position), context)
    }

    class PrevViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(item: Events, context: Context) {
            itemView.tvEventLeague.text = item.strEvent
            itemView.tvDateEvent.text = item.dateEvent
            itemView.tvHomeNameTeam.text = item.strHomeTeam
            itemView.tvAwayNameTeam.text = item.strAwayTeam
            itemView.tvScoreHome.text = item.intHomeScore
            itemView.tvScoreAway.text = item.intAwayScore

            itemView.setOnClickListener {
                val intent = Intent(context, DetailJadwalActivity::class.java)
                intent.putExtra(ID_EVENT, item.idEvent)
                context.startActivity(intent)
            }
        }

    }
}