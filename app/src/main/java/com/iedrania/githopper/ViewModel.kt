package com.iedrania.githopper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel : ViewModel() {
    private val _listUser = MutableLiveData<List<UserResponse>>()
    val listUser: LiveData<List<UserResponse>> = _listUser
    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user
    private val _listFollowers = MutableLiveData<List<UserResponse>>()
    val listFollowers: LiveData<List<UserResponse>> = _listFollowers
    private val _listFollowing = MutableLiveData<List<UserResponse>>()
    val listFollowing: LiveData<List<UserResponse>> = _listFollowing
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "ViewModel"
    }

    fun findUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findFollow(username: String, position: Int) {
        _isLoading.value = true
        var client: Call<List<UserResponse>>? = null
        when (position) {
            0 -> client = ApiConfig.getApiService().getFollowers(username)
            1 -> client = ApiConfig.getApiService().getFollowing(username)
        }
        client!!.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    when (position) {
                        0 -> _listFollowers.value = response.body()
                        1 -> _listFollowing.value = response.body()
                    }
                } else {
                    Log.e(TAG, "b onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "a onFailure: ${t.message}")
            }
        })
    }
}
