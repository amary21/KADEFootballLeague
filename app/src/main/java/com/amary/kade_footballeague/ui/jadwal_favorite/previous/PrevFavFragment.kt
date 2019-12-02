package com.amary.kade_footballeague.ui.jadwal_favorite.previous

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.amary.kade_footballeague.R
import kotlinx.android.synthetic.main.fragment_prev_fav.*

class PrevFavFragment : Fragment() {

    private lateinit var viewModel: PrevFavViewModel
    private var prevAdapter : PrevFavAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_prev_fav, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PrevFavViewModel::class.java)

        //set recyclerview
        prevAdapter = PrevFavAdapter(context as Activity)
        rvFavPrevious.layoutManager = LinearLayoutManager(context)
        rvFavPrevious.setHasFixedSize(true)
        rvFavPrevious.adapter = prevAdapter
    }

}
