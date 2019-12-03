package com.amary.kade_footballeague.ui.schedule_favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.ui.schedule_favorite.next.NextFavFragment
import com.amary.kade_footballeague.ui.schedule_favorite.previous.PrevFavFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import kotlinx.android.synthetic.main.activity_schedule_fav.*

class ScheduleFavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_fav)

        setupViewPager(jadwalContainerFav)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ScheduleFavTabAdapter(supportFragmentManager)

        val fragNext = NextFavFragment()
        val fragPrev = PrevFavFragment()

        adapter.addFrag(fragNext)
        adapter.addFrag(fragPrev)

        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabMenuJadwalFav))
        tabMenuJadwalFav.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
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
