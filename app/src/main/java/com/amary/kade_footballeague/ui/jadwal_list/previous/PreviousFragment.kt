package com.amary.kade_footballeague.ui.jadwal_list.previous

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.rest.ApiClient
import com.amary.kade_footballeague.rest.ApiRepository
import com.amary.kade_footballeague.rest.ID_LEAGUE
import kotlinx.android.synthetic.main.fragment_previous.*

@Suppress("UNCHECKED_CAST")
class PreviousFragment : Fragment() {

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: PreviousViewModel
    private var prevAdapter : PreviousAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_previous, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = this.arguments
        val idLeague = bundle?.getString(ID_LEAGUE)

        apiRepository = ApiRepository(apiService)
        viewModel = getViewModel(apiRepository)
        prevAdapter = PreviousAdapter(context as Activity)

        rvPrevious.layoutManager = LinearLayoutManager(context)
        rvPrevious.setHasFixedSize(true)
        rvPrevious.adapter = prevAdapter

        if (idLeague != null) {
            viewModel.getPrevMatch(idLeague).observe(this, Observer {
                if (it.events != null){
                    prevAdapter?.setEvent(it.events)
                    rvPrevious.visibility = View.VISIBLE
                    smPrevMatch.stopShimmer()
                    smPrevMatch.visibility = View.GONE
                }
            })
        }
    }

    private fun getViewModel(apiRepository: ApiRepository): PreviousViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return PreviousViewModel(apiRepository) as T
            }

        })[PreviousViewModel::class.java]
    }

}
