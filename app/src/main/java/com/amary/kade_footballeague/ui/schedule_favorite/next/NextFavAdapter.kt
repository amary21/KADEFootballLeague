package com.amary.kade_footballeague.ui.schedule_favorite.next

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.local.model.NextMatch
import com.amary.kade_footballeague.data.rest.ID_EVENT
import com.amary.kade_footballeague.data.rest.IS_NEXT
import com.amary.kade_footballeague.data.rest.IS_PREV
import com.amary.kade_footballeague.ui.detail_schedule.DetailScheduleActivity
import com.amary.kade_footballeague.utils.DateConvert
import com.amary.kade_footballeague.utils.GlideApp
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_schedule.view.*

class NextFavAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var event : MutableList<NextMatch> = ArrayList()

    fun setEvent(events: List<NextMatch>) {
        this.event = events as MutableList<NextMatch>
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): NextMatch {
        return event[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return NextFavViewHolder(view)
    }


    override fun getItemCount(): Int {
        return event.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NextFavViewHolder).bind(getItem(position), context)
    }


    class NextFavViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: NextMatch, context: Context) {
            val dateConvert = DateConvert()
            itemView.tvEventLeague.text = item.strEvent
            itemView.tvDateEvent.text = item.dateEvent?.let { dateConvert.convertDate(it) }
            itemView.tvTimeEvent.text = item.strTime?.let { dateConvert.convertTime(it) }
            itemView.tvHomeNameTeam.text = item.strHomeTeam
            itemView.tvAwayNameTeam.text = item.strAwayTeam
            itemView.tvScoreHome.text = "-"
            itemView.tvScoreAway.text = "-"


            itemView.setOnClickListener {
                val intent = Intent(context, DetailScheduleActivity::class.java)
                intent.putExtra(ID_EVENT, item.idEvent)
                intent.putExtra(IS_NEXT, true)
                intent.putExtra(IS_PREV, false)
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


            itemView.txtStatus.visibility = View.GONE
        }

    }
}