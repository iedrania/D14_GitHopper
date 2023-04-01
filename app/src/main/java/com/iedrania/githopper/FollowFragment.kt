package com.iedrania.githopper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iedrania.githopper.databinding.FragmentFollowBinding
import com.iedrania.githopper.ui.detail.DetailViewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var username: String
    private var position: Int = 0

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())

        position = arguments?.getInt(ARG_POSITION)!!
        username = arguments?.getString(ARG_USERNAME)!!

        detailViewModel = ViewModelProvider(
            requireActivity(), ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]
        when (position) {
            0 -> {
                detailViewModel.listFollowers.observe(viewLifecycleOwner) { listUser ->
                    setFollowData(listUser)
                }
            }
            1 -> {
                detailViewModel.listFollowing.observe(viewLifecycleOwner) { listUser ->
                    setFollowData(listUser)
                }
            }
        }
        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        detailViewModel.isError.observe(viewLifecycleOwner) {
            showError(it)
        }
    }

    private fun setFollowData(users: List<UserResponse>) {
        val listUser = ArrayList<UserResponse>()
        for (user in users) {
            listUser.add(
                UserResponse(user.login, user.avatarURL, user.name, user.followers, user.following)
            )
        }
        if (listUser.isEmpty()) {
            binding.searchHint.text = getString(R.string.no_users)
            binding.rvFollow.adapter = null
        } else {
            binding.searchHint.visibility = View.GONE
            binding.rvFollow.adapter = UserAdapter(listUser)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.searchHint.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(isError: Boolean) {
        when (position) {
            0 -> if (isError) Toast.makeText(
                requireActivity(),
                "Error displaying followers list. Please try again later.",
                Toast.LENGTH_LONG
            ).show()
            1 -> if (isError) Toast.makeText(
                requireActivity(),
                "Error displaying following list. Please try again later.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.findFollow(username, position)
    }
}