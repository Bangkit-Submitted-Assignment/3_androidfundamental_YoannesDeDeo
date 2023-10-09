package com.dicoding.aplikasigithub.ui.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aplikasigithub.database.FavoriteUser
import com.dicoding.aplikasigithub.repository.FavUserRepository

class FavViewModel(application: Application) : ViewModel() {
    private val mFavUserRepository: FavUserRepository = FavUserRepository(application)

    fun insert(favUser: FavoriteUser) {
        mFavUserRepository.insert(favUser)
    }

    fun delete(favUser: FavoriteUser) {
        mFavUserRepository.delete(favUser)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> =
        mFavUserRepository.getFavoriteUserByUsername(username)
}