package com.amary.kade_footballeague.ui.jadwal_favorite.next

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.amary.kade_footballeague.R
import kotlinx.android.synthetic.main.fragment_next_fav.*

class NextFavFragment : Fragment() {

    private lateinit var viewModel: NextFavViewModel
    private var nextAdapter : NextFavAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_next_fav, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NextFavViewModel::class.java)

        //set recyclerview
        nextAdapter = NextFavAdapter(context as Activity)
        rvFavNext.layoutManager = LinearLayoutManager(context)
        rvFavNext.setHasFixedSize(true)
        rvFavNext.adapter = nextAdapter
    }

}
