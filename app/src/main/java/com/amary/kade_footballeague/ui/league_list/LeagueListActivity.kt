package com.amary.kade_footballeague.ui.league_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.data.rest.ID_LEAGUE
import com.amary.kade_footballeague.data.rest.ID_LEAGUE_SAVE
import com.amary.kade_footballeague.ui.league_list.next.NextFragment
import com.amary.kade_footballeague.ui.league_list.previous.PrevFragment
import com.amary.kade_footballeague.ui.league_list.standings.StandingsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import kotlinx.android.synthetic.main.activity_league.*

class LeagueListActivity : AppCompatActivity() {

    private var idJadwal : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league)

        idJadwal = if (savedInstanceState == null){
            intent.getStringExtra(ID_LEAGUE)
        }else{
            savedInstanceState.getString(ID_LEAGUE_SAVE)
        }


        setupViewPager(scheduleContainer, idJadwal)
    }

    private fun setupViewPager(viewPager: ViewPager, id: String?) {
        val adapter = LeagueListTabAdapter(supportFragmentManager)

        val bundle = Bundle()
        bundle.putString(ID_LEAGUE, id)

        val fragNext = NextFragment()
        val fragPrev = PrevFragment()
        val fragStand = StandingsFragment()

        fragNext.arguments = bundle
        fragPrev.arguments = bundle
        fragStand.arguments = bundle

        adapter.addFrag(fragNext)
        adapter.addFrag(fragPrev)
        adapter.addFrag(fragStand)

        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabMenuSchedule))
        tabMenuSchedule.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(ID_LEAGUE_SAVE, idJadwal)
        super.onSaveInstanceState(outState)
    }
}
