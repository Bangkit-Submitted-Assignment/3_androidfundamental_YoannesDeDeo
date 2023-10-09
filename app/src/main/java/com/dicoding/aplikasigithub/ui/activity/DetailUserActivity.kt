package com.dicoding.aplikasigithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.aplikasigithub.R
import com.dicoding.aplikasigithub.data.response.DetailUserResponse
import com.dicoding.aplikasigithub.database.FavoriteUser
import com.dicoding.aplikasigithub.databinding.ActivityDetailUserBinding
import com.dicoding.aplikasigithub.databinding.ActivityFavoriteBinding
import com.dicoding.aplikasigithub.ui.adapter.SectionPagerAdapter
import com.dicoding.aplikasigithub.ui.viewModel.DetailViewModel
import com.dicoding.aplikasigithub.ui.viewModel.FavViewModel
import com.dicoding.aplikasigithub.ui.view_model_factory.FavViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var favUserViewModel: FavViewModel
    private var _activityFavBinding: ActivityFavoriteBinding? = null

    private var login: String? = null
    private lateinit var share: Button

    private var isFail = false
    private var favUser: FavoriteUser? = null

    companion object {
        const val LOGIN = "login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        login = intent.getStringExtra(LOGIN).toString()

        supportActionBar?.title = "Show Detail - ${login.toString()}"

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        viewPager.adapter = sectionsPagerAdapter
        sectionsPagerAdapter.username = login
        val tabs: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.followers)
                1 -> tab.text = resources.getString(R.string.following)
                else -> tab.text = "Nothing"
            }
        }.attach()
        supportActionBar?.elevation = 0f

        detailViewModel.getDetail(login!!)

        detailViewModel.detailResponse.observe(this) { show ->
            showDataDetail(show)
            favUser?.username = show.login.toString()
            favUser?.avatarUrl = show.avatarUrl
        }

        detailViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        favUserViewModel = obtainViewModel(this@DetailUserActivity)

        favUser = intent.getParcelableExtra(LOGIN)
        if (favUser != null) {
            isFail = true
        } else {
            favUser = FavoriteUser()
        }

        favUserViewModel.getFavoriteUserByUsername(login.toString()).observe(this, Observer {
            if (it != null) {
                binding.addFavorite.setImageResource(R.drawable.onfavorite)
                binding.addFavorite.setOnClickListener {
                    deleteItemDb()
                }
            } else {
                binding.addFavorite.setImageResource(R.drawable.unfavorite)
                binding.addFavorite.setOnClickListener {
                    addToDb()
                }
            }
        })
        showShare()
    }

    private fun addToDb() {
        favUserViewModel.insert(favUser as FavoriteUser)
        showToast(getString(R.string.added, login.toString()))
    }

    private fun deleteItemDb() {
        favUserViewModel.delete(favUser as FavoriteUser)
        showToast(getString(R.string.delete, login.toString()))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavViewModel::class.java)
    }

    private fun showDataDetail(show: DetailUserResponse) {
        Glide.with(this)
            .load(show.avatarUrl)
            .into(binding.imgItemPhoto)
        binding.name.text = show.name
        binding.username.text = show.login
        binding.followers.text = StringBuilder(show.followers.toString()).append(" Followers")
        binding.following.text = StringBuilder(show.following.toString()).append(" Following")
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showShare() {
        share = findViewById(R.id.action_share)
        share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "type/plaim"
            val intentBody = "Terima kasih"
            val intentSub = "share user is done"
            intent.putExtra(Intent.EXTRA_SUBJECT, intentBody)
            intent.putExtra(Intent.EXTRA_TEXT, intentSub)
            startActivity(Intent.createChooser(intent, "Share now!!"))
        }
    }
}