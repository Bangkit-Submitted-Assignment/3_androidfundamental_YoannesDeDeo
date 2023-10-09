package com.dicoding.aplikasigithub.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.aplikasigithub.database.FavoriteUser
import com.dicoding.aplikasigithub.databinding.ListItemBinding
import com.dicoding.aplikasigithub.helper.FavUserDiffCallback
import com.dicoding.aplikasigithub.ui.activity.DetailUserActivity

class FavoriteAdapter : ListAdapter<FavoriteUser, FavoriteAdapter.FavViewHolder>(DIFF_CALLBACK) {
    private val listFavorite = ArrayList<FavoriteUser>()

    fun setListFav(listfav: List<FavoriteUser>) {
        val diffCallback = FavUserDiffCallback(this.listFavorite, listfav)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listfav)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val userFav = listFavorite[position]
        holder.bind(userFav)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.LOGIN, userFav.username)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    inner class FavViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fav: FavoriteUser) {
            binding.tvItemName.text = fav.username
            Glide.with(binding.root.context)
                .load(fav.avatarUrl)
                .into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}