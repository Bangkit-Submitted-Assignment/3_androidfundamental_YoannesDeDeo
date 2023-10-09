package com.dicoding.aplikasigithub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasigithub.databinding.FragmentFollowBinding
import com.dicoding.aplikasigithub.ui.adapter.RvFollowAdapter
import com.dicoding.aplikasigithub.ui.viewModel.FollowViewModel

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private var position: Int = 0
    private var username: String? = null
    private val followViewModel by viewModels<FollowViewModel>()

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        val followersAdapter = RvFollowAdapter()
        val followingAdapter = RvFollowAdapter()

        if (position == 1) {
            binding.RvFollowers.apply {
                adapter = followersAdapter
                layoutManager = LinearLayoutManager(requireActivity())
                setHasFixedSize(true)
            }
            showLoading()
            followViewModel.resultFollowers(username!!)
            followViewModel.followersList.observe(viewLifecycleOwner) { followers ->
                followersAdapter.submitList(followers)
                hideLoading()
            }
        } else {
            binding.RvFollowers.apply {
                adapter = followingAdapter
                layoutManager = LinearLayoutManager(requireActivity())
                setHasFixedSize(true)
            }
            showLoading()
            followViewModel.resultFollowing(username!!)
            followViewModel.followingList.observe(viewLifecycleOwner) { following ->
                followingAdapter.submitList(following)
                hideLoading()
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

}



