package com.amary.kade_footballeague.ui.jadwal_favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.ui.jadwal_favorite.next.NextFavFragment
import com.amary.kade_footballeague.ui.jadwal_favorite.previous.PreviousFavFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import kotlinx.android.synthetic.main.activity_jadwal_fav.*

class JadwalFavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal_fav)

        setupViewPager(jadwalContainerFav)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = JadwalFavTabAdapter(supportFragmentManager)

//        val bundle = Bundle()
//        bundle.putString(ID_LEAGUE, id)

        val fragNext = NextFavFragment()
        val fragPrev = PreviousFavFragment()

//        fragNext.arguments = bundle
//        fragPrev.arguments = bundle

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
