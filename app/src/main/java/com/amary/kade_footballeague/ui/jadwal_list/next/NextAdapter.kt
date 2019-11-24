package com.amary.kade_footballeague.ui.jadwal_list.next

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
import kotlinx.android.synthetic.main.item_jadwal.view.*

class NextAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

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
        return NextViewHolder(view)
    }

    override fun getItemCount(): Int {
        return event.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NextViewHolder).bind(getItem(position), context)
    }

    class NextViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(item: Events, context: Context) {

            itemView.tvEventLeague.text = item.strEvent
            itemView.tvDateEvent.text = item.dateEvent
            itemView.tvHomeNameTeam.text = item.strHomeTeam
            itemView.tvAwayNameTeam.text = item.strAwayTeam
            if (item.intHomeScore == null && item.intAwayScore == null) {
                itemView.tvScoreHome.text = "-"
                itemView.tvScoreAway.text = "-"
            }

            itemView.setOnClickListener {
                val intent = Intent(context, DetailJadwalActivity::class.java)
                intent.putExtra(ID_EVENT, item.idEvent)
                context.startActivity(intent)
            }

            itemView.txtStatus.visibility = View.GONE
        }
    }
}