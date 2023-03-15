package com.iedrania.githopper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iedrania.githopper.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())

        val position = arguments?.getInt(ARG_POSITION)!!
        val username = arguments?.getString(ARG_USERNAME)!!

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ViewModel::class.java]
        viewModel.findFollow(username, position)
        when (position) {
            0 -> {
                viewModel.listFollowers.observe(viewLifecycleOwner) { listUser ->
                    setFollowData(listUser)
                }
            }
            1 -> {
                viewModel.listFollowing.observe(viewLifecycleOwner) { listUser ->
                    setFollowData(listUser)
                }
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setFollowData(users: List<UserResponse>) {
        val listUser = ArrayList<UserResponse>()
        for (user in users) {
            listUser.add(
                UserResponse(user.login, user.avatarURL, user.name, user.followers, user.following)
            )
        }
        val adapter = UserAdapter(listUser)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}