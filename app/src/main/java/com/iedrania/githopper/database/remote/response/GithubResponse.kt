package com.iedrania.githopper.database.remote.response

import com.google.gson.annotations.SerializedName

data class GithubResponse(
    @field:SerializedName("total_count") val totalCount: Long,

    @field:SerializedName("incomplete_results") val incompleteResults: Boolean,

    @field:SerializedName("items") val items: List<UserResponse>
)
