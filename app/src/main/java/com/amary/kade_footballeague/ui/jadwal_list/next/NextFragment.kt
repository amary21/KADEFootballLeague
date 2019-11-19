package com.amary.kade_footballeague.ui.jadwal_list.next

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_next.*

@Suppress("UNCHECKED_CAST")
class NextFragment : Fragment() {


    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: NextViewModel
    private var nextAdapter : NextAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_next, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = this.arguments
        val idLeague = bundle?.getString(ID_LEAGUE)

        apiRepository = ApiRepository(apiService)
        viewModel = getViewModel(apiRepository)
        nextAdapter = NextAdapter(context as Activity)

        rvNext.layoutManager = LinearLayoutManager(context)
        rvNext.setHasFixedSize(true)
        rvNext.adapter = nextAdapter

        if (idLeague != null) {
            viewModel.getNextMatch(idLeague).observe(this, Observer {
                if(it.events != null){
                    nextAdapter?.setEvent(it.events)
                    rvNext.visibility = View.VISIBLE
                    smNextMatch.stopShimmer()
                    smNextMatch.visibility = View.GONE

                }
            })
        }

    }

    private fun getViewModel(apiRepository: ApiRepository): NextViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NextViewModel(apiRepository) as T
            }

        })[NextViewModel::class.java]
    }

}
