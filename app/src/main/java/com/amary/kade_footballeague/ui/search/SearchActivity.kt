package com.amary.kade_footballeague.ui.search

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.amary.kade_footballeague.R
import com.amary.kade_footballeague.rest.ApiClient
import com.amary.kade_footballeague.rest.ApiRepository
import kotlinx.android.synthetic.main.activity_search.*

@Suppress("UNCHECKED_CAST")
class SearchActivity : AppCompatActivity() {

    private val apiService = ApiClient.getClient()
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: SearchViewModel
    private var adapter : SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        apiRepository = ApiRepository(apiService)
        adapter = SearchAdapter(this)
        viewModel = getViewModel(apiRepository)

        rvSearch.layoutManager = LinearLayoutManager(this)
        rvSearch.setHasFixedSize(true)
        rvSearch.isNestedScrollingEnabled = false
        rvSearch.adapter = adapter

        rvSearch.visibility = View.GONE
        imgSearch404.visibility = View.VISIBLE
        txtSearch404.visibility = View.VISIBLE
    }

    private fun getSearchEvent(search: String) {
        viewModel.getSearchEvent(search).observe(this, Observer {
            if (it != null){
                adapter?.setEvent(it.events)
            }
        })
    }

    private fun getViewModel(apiRepository: ApiRepository): SearchViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SearchViewModel(apiRepository) as T
            }

        })[SearchViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_app_bar, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.mn_app_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            @SuppressLint("DefaultLocale")
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.toLowerCase()
                if (newText != null && newText != ""){
                    getSearchEvent(newText)
                    rvSearch.visibility = View.VISIBLE
                    imgSearch404.visibility = View.GONE
                    txtSearch404.visibility = View.GONE
                }
                return true
            }

        })

        return true
    }

}
