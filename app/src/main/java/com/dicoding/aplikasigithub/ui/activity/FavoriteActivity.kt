package com.dicoding.aplikasigithub.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasigithub.R
import com.dicoding.aplikasigithub.databinding.ActivityFavoriteBinding
import com.dicoding.aplikasigithub.ui.adapter.FavoriteAdapter
import com.dicoding.aplikasigithub.ui.view_model_factory.FavViewModelFactory
import com.dicoding.aplikasigithub.ui.viewModel.FavoriteUserViewModel as FavoriteUserViewModel1

class FavoriteActivity : AppCompatActivity() {
    private var _FavoriteActivityBinding: ActivityFavoriteBinding? = null
    private val binding get() = _FavoriteActivityBinding

    private lateinit var adapter: FavoriteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _FavoriteActivityBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val noAction = supportActionBar
        noAction?.title = getString(R.string.favorite_button)

        val favViewModel = obtainViewModel(this@FavoriteActivity)
        favViewModel.getAllUser().observe(this) { favList ->
            adapter.setListFav(favList)
        }

        adapter = FavoriteAdapter()

        binding?.rvFavorite?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavorite?.setHasFixedSize(true)
        binding?.rvFavorite?.adapter = adapter

        binding?.rvFavorite?.setOnClickListener {
            val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _FavoriteActivityBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel1 {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteUserViewModel1::class.java)
    }
}