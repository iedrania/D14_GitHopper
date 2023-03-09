package com.iedrania.githopper

import com.google.gson.annotations.SerializedName

data class GitHubResponse (
    @field:SerializedName("total_count")
    val totalCount: Long,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<UserItem>
)

// TODO unnecessary fields
data class UserItem (
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("node_id")
    val nodeID: String,

    @field:SerializedName("avatar_url")
    val avatarURL: String,

    @field:SerializedName("gravatar_id")
    val gravatarID: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("html_url")
    val htmlURL: String,

    @field:SerializedName("followers_url")
    val followersURL: String,

    @field:SerializedName("following_url")
    val followingURL: String,

    @field:SerializedName("gists_url")
    val gistsURL: String,

    @field:SerializedName("starred_url")
    val starredURL: String,

    @field:SerializedName("subscriptions_url")
    val subscriptionsURL: String,

    @field:SerializedName("organizations_url")
    val organizationsURL: String,

    @field:SerializedName("repos_url")
    val reposURL: String,

    @field:SerializedName("events_url")
    val eventsURL: String,

    @field:SerializedName("received_events_url")
    val receivedEventsURL: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("site_admin")
    val siteAdmin: Boolean,

    @field:SerializedName("score")
    val score: Long
)
