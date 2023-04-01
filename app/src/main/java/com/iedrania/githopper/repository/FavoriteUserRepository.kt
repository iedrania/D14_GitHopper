package com.iedrania.githopper.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.iedrania.githopper.database.local.entity.FavoriteUser
import com.iedrania.githopper.database.local.room.FavoriteUserDao
import com.iedrania.githopper.database.local.room.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUsersDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUsersDao = db.favoriteUserDao()
    }

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUsersDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUsersDao.delete(favoriteUser) }
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> =
        mFavoriteUsersDao.getAllFavoriteUsers()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> =
        mFavoriteUsersDao.getFavoriteUserByUsername(username)
}