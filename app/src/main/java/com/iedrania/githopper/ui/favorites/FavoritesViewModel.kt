package com.iedrania.githopper.ui.favorites

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.iedrania.githopper.database.FavoriteUser
import com.iedrania.githopper.repository.FavoriteUserRepository

class FavoritesViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> =
        mFavoriteUserRepository.getAllFavoriteUsers()
}