package com.amary.kade_footballeague.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.rest.ApiClient
import com.amary.kade_footballeague.rest.ApiRepository
import com.amary.kade_footballeague.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: MainViewModel
    private var mainAdapter : MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiRepository = ApiRepository(apiService)
        mainAdapter = MainAdapter(this)
        viewModel = getViewModel(apiRepository)

        rvLeague.layoutManager = LinearLayoutManager(this)
        rvLeague.setHasFixedSize(true)
        rvLeague.isNestedScrollingEnabled = false
        rvLeague.adapter = mainAdapter

        viewModel.getLeague().observe(this, Observer {
            if (it != null){
                mainAdapter?.setLeagues(it.leagues)
                imgCover.visibility = View.VISIBLE
                txtCover.visibility = View.VISIBLE
                rvLeague.visibility = View.VISIBLE
                smMain.stopShimmer()
                smMain.visibility = View.GONE

            }

        })

        fabSearchEvent.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getViewModel(apiRepository: ApiRepository): MainViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(apiRepository) as T
            }

        })[MainViewModel::class.java]
    }
}
