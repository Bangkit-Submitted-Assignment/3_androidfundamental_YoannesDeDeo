package com.dicoding.aplikasigithub.ui.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aplikasigithub.database.FavoriteUser
import com.dicoding.aplikasigithub.repository.FavUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val mFavUserRepository: FavUserRepository = FavUserRepository(application)
    fun getAllUser(): LiveData<List<FavoriteUser>> = mFavUserRepository.getAllUser()
}