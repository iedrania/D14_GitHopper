package com.iedrania.githopper

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Call<User>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ListUser>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ListUser>
}