package com.iedrania.githopper.ui.favorites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iedrania.githopper.UserResponse
import com.iedrania.githopper.databinding.ActivityFavoritesBinding
import com.iedrania.githopper.helper.ViewModelFactory

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding

    private lateinit var adapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favoritesViewModel = obtainViewModel(this@FavoritesActivity)
        favoritesViewModel.getAllFavoriteUsers().observe(this) { favoriteUserList ->
            if (favoriteUserList != null) {
                favoriteUserList.map {
                    UserResponse(
                        login = it.username, avatarURL = it.avatarUrl, null, null, null
                    )
                }
                adapter.setListFavoriteUsers(favoriteUserList)
            }
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        adapter = FavoritesAdapter()
        binding.rvFavorites.adapter = adapter
        binding.rvFavorites.layoutManager = LinearLayoutManager(this)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoritesViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoritesViewModel::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}