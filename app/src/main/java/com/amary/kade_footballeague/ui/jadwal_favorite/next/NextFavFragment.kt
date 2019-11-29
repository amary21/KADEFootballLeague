package com.amary.kade_footballeague.ui.jadwal_favorite.next

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.amary.kade_footballeague.R

class NextFavFragment : Fragment() {

    companion object {
        fun newInstance() = NextFavFragment()
    }

    private lateinit var viewModel: NextFavViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_next_fav, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NextFavViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
