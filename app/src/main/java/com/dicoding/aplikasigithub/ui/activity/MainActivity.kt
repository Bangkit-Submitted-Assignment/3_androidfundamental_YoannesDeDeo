package com.dicoding.aplikasigithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasigithub.R
import com.dicoding.aplikasigithub.data.response.ItemsItem
import com.dicoding.aplikasigithub.data_store.SettingPreferences
import com.dicoding.aplikasigithub.data_store.dataStore
import com.dicoding.aplikasigithub.databinding.ActivityMainBinding
import com.dicoding.aplikasigithub.ui.adapter.RvAdapter
import com.dicoding.aplikasigithub.ui.viewModel.MainViewModel
import com.dicoding.aplikasigithub.ui.viewModel.ModeViewModel
import com.dicoding.aplikasigithub.ui.view_model_factory.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var loadingProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        loadingProgressBar = binding.progressBar
        setContentView(binding.root)

        mainViewModel.userResponse.observe(this) { userResponse ->
            val itemsItemList = userResponse?.items ?: emptyList()
            setReviewData(itemsItemList)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.listReview.observe(this) { consumerReviews ->
            setReviewData(consumerReviews)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        eventClickItem()

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel =
            ViewModelProvider(this, ViewModelFactory(pref)).get(ModeViewModel::class.java)

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    private fun eventClickItem() {
        with(binding) {
            searchBar.inflateMenu(R.menu.menu_main)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.buttonFavorit -> {
                        val moveMode = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(moveMode)
                        true
                    }

                    R.id.buttonMode -> {
                        val moveMode = Intent(this@MainActivity, ModeActivity::class.java)
                        startActivity(moveMode)
                        true
                    }

                    else -> false
                }
            }
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    mainViewModel.resultSearch(searchBar.text.toString())
                    false
                }
        }
    }

    private fun setReviewData(consumerReviews: List<ItemsItem>) {
        val adapter = RvAdapter(this)
        adapter.submitList(consumerReviews)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    fun getLoadingProgressBar(): ProgressBar {
        return loadingProgressBar
    }

    override fun onResume() {
        super.onResume()
        showLoading(false)
    }

}