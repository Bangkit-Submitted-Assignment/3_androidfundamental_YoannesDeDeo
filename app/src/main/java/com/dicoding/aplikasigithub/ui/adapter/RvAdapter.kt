package com.dicoding.aplikasigithub.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.aplikasigithub.data.response.ItemsItem
import com.dicoding.aplikasigithub.databinding.ListItemBinding
import com.dicoding.aplikasigithub.ui.activity.DetailUserActivity
import com.dicoding.aplikasigithub.ui.activity.MainActivity

class RvAdapter(private val mainActivity: MainActivity) :
    ListAdapter<ItemsItem, RvAdapter.MyViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            val progressBar = mainActivity.getLoadingProgressBar()
            progressBar.visibility = View.VISIBLE
            val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.LOGIN, user.login)
            holder.itemView.context.startActivity(intent)
        }
    }

    class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ItemsItem) {
            val img = review.avatarUrl
            binding.tvItemName.text = "${review.login}"
            Glide.with(binding.root.context)
                .load(img)
                .into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}