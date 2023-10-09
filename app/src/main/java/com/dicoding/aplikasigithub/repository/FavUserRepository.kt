package com.dicoding.aplikasigithub.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.aplikasigithub.database.FavRoomDatabase
import com.dicoding.aplikasigithub.database.FavoriteUser
import com.dicoding.aplikasigithub.database.FavoriteUserDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepository(application: Application) {
    private val mFavUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavRoomDatabase.getDatabase(application)
        mFavUserDao = db.favDao()
    }

    fun getAllUser(): LiveData<List<FavoriteUser>> = mFavUserDao.getAllUser()

    fun insert(favUser: FavoriteUser) {
        executorService.execute { mFavUserDao.insert(favUser) }
    }

    fun delete(favUser: FavoriteUser) {
        executorService.execute { mFavUserDao.delete(favUser) }
    }

    fun update(favUser: FavoriteUser) {
        executorService.execute { mFavUserDao.update(favUser) }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> =
        mFavUserDao.getFavoriteUserByUsername(username)
}