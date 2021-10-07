package com.example.mviarchitecture


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mviarchitecture.R
import com.example.mviarchitecture.adapter.MainAdapter
import com.example.mviarchitecture.api.ApiHelperImpl
import com.example.mviarchitecture.api.RetrofitBuilder
import com.example.mviarchitecture.intent.MainIntent
import com.example.mviarchitecture.model.MainViewModel
import com.example.mviarchitecture.model.User
import com.example.mviarchitecture.model.ViewModelFactory
import com.example.mviarchitecture.viewstate.MainState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var buttonFetchUser: Button
    private lateinit var recyclerView: RecyclerView
    private var adapter = MainAdapter(arrayListOf())
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        buttonFetchUser = buttonFetchUser1
        buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser)
            }

        }
    }

    private fun setupUI() {
        recyclerView = my_recyclerView//=recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as
                            LinearLayoutManager).orientation
                )
            )
        }
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun observeViewModel() {
        progressBar = progressBar1

        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {
                    }
                    is MainState.Loading -> {
                        buttonFetchUser.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Users -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.GONE
                        renderList(it.user)
                    }
                    is MainState.Error -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.VISIBLE
                        Toast.makeText(
                            this@MainActivity, it.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun renderList(users: List<User>) {
        recyclerView.visibility = View.VISIBLE
        users.let { listOfUsers ->
            listOfUsers.let {
                adapter.addData(it)
            }
        }
        adapter.notifyDataSetChanged()
    }
}