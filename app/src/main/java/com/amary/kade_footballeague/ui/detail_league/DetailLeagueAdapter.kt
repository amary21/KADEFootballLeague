package com.amary.kade_footballeague.ui.detail_league

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.rest.response.model.FanArt
import com.amary.kade_footballeague.utils.GlideApp
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_fanart.view.*

class DetailLeagueAdapter(private val context: Context) : PagerAdapter() {

    private var fanart: MutableList<FanArt> = ArrayList()

    fun setLeagues(fanart: List<FanArt>) {
        this.fanart.addAll(fanart)
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return fanart.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_fanart, container, false)

        GlideApp.with(view.context)
            .load(fanart[position].urlImage)
            .apply(RequestOptions.placeholderOf(R.drawable.image_placeholder).error(R.drawable.image_placeholder))
            .into(view.imgFanart)


        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}