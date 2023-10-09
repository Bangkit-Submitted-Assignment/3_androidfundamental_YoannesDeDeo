package com.dicoding.aplikasigithub.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.aplikasigithub.database.FavoriteUser

class FavUserDiffCallback(
    private val oldListFav: List<FavoriteUser>,
    private val newListFav: List<FavoriteUser>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldListFav.size
    override fun getNewListSize(): Int = newListFav.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListFav[oldItemPosition].username == newListFav[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavUser = oldListFav[oldItemPosition]
        val newFavUser = newListFav[newItemPosition]
        return oldFavUser.username == newFavUser.username && oldFavUser.avatarUrl == newFavUser.avatarUrl
    }
}