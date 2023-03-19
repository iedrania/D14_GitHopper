package com.iedrania.githopper

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q") username: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUser(
        @Path("username") username: String
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<UserResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UserResponse>>
}