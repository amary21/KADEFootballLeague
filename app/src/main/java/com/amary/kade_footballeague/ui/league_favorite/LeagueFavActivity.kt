package com.amary.kade_footballeague.ui.league_favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.ui.league_favorite.next.NextFavFragment
import com.amary.kade_footballeague.ui.league_favorite.previous.PrevFavFragment
import com.amary.kade_footballeague.ui.league_favorite.teams.TeamFavFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import kotlinx.android.synthetic.main.activity_schedule_fav.*

class LeagueFavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_fav)

        setupViewPager(scheduleContainerFav)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = LeagueFavTabAdapter(supportFragmentManager)

        val fragNext = NextFavFragment()
        val fragPrev = PrevFavFragment()
        val fragTeam = TeamFavFragment()

        adapter.addFrag(fragNext)
        adapter.addFrag(fragPrev)
        adapter.addFrag(fragTeam)

        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabMenuScheduleFav))
        tabMenuScheduleFav.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0 != null) {
                    viewPager.currentItem = p0.position
                }
            }

        })
    }
}
