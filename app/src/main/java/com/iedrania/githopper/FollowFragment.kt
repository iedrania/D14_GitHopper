package com.iedrania.githopper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.iedrania.githopper.databinding.FragmentFollowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(EXTRA_USERNAME)
        findFollows(username!!, index!!)
    }

    companion object {
        private const val TAG = "FollowFragment"
        const val ARG_SECTION_NUMBER = "section_number"
        const val EXTRA_USERNAME = "extra_username"
    }

    private fun findFollows(username: String, index: Int) {
        showLoading(true)
        var client: Call<ListUser>? = null
        when (index) {
            0 -> {
                client = ApiConfig.getApiService().getFollowers(username)
                println()
            }
            1 -> client = ApiConfig.getApiService().getFollowing(username)
        }
        client!!.enqueue(object : Callback<ListUser> {
            override fun onResponse(
                call: Call<ListUser>,
                response: Response<ListUser>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUsersData(responseBody)
                    }
                } else {
                    Log.e(TAG, "b onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ListUser>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "a onFailure: ${t.message}")
            }
        })
    }

    private fun setUsersData(users: List<User>) {
        val listUser = ArrayList<User>()
        for (user in users) {
            listUser.add(
                User(user.login, user.avatarURL, user.name, user.followers, user.following)
            )
        }
        val adapter = UserAdapter(listUser)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}