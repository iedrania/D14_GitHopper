package com.iedrania.githopper

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @field:SerializedName("total_count")
    val totalCount: Long,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<User>
)

data class User (
    @field:SerializedName("login")
    val login: String, // TODO user doesn't exist

    @field:SerializedName("avatar_url")
    val avatarURL: String?, // TODO nullable

    @field:SerializedName("name")
    val name: String?, // TODO nullable

    @field:SerializedName("followers")
    val followers: Long?,

    @field:SerializedName("following")
    val following: Long?
)

typealias Followers = List<User>

typealias Following = List<User>
