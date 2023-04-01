package com.iedrania.githopper

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    @field:SerializedName("login") val login: String,

    @field:SerializedName("avatar_url") val avatarURL: String?,

    @field:SerializedName("name") val name: String?,

    @field:SerializedName("followers") val followers: Long?,

    @field:SerializedName("following") val following: Long?
) : Parcelable
