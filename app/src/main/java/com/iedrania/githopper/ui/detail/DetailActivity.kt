package com.iedrania.githopper.ui.detail

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.iedrania.githopper.R
import com.iedrania.githopper.database.remote.response.UserResponse
import com.iedrania.githopper.database.local.entity.FavoriteUser
import com.iedrania.githopper.databinding.ActivityDetailBinding
import com.iedrania.githopper.helper.ViewModelFactory
import com.iedrania.githopper.helper.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers_tab_text, R.string.following_tab_text
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userObject = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_USER, UserResponse::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra(EXTRA_USER)
        }

        detailViewModel = obtainViewModel(this@DetailActivity)
        detailViewModel.user.observe(this) { user ->
            setUserDetails(user)
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        detailViewModel.isError.observe(this) {
            showError(it)
        }
        detailViewModel.getUserDetail(userObject!!.login)
        detailViewModel.getFavoriteUserByUsername(userObject.login).observe(this) { user ->
            if (user == null) {
                binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
            } else {
                binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
            }
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = userObject.login
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val pref = SettingPreferences.getInstance(dataStore)
        val factory = ViewModelFactory.getInstance(activity.application, pref)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun setUserDetails(user: UserResponse) {
        if (user.name == null) {
            binding.tvDetailName.visibility = View.GONE
            binding.space2.visibility = View.GONE
        } else {
            binding.tvDetailName.text = user.name
        }

        binding.tvDetailUsername.text = user.login

        when (user.followers!!.toInt()) {
            1 -> "${user.followers} Follower · ${user.following} Following".also {
                binding.tvDetailStats.text = it
            }
            else -> "${user.followers} Followers · ${user.following} Following".also {
                binding.tvDetailStats.text = it
            }
        }

        if (user.avatarURL == null) {
            binding.imgDetailPhoto.visibility = View.GONE
        } else {
            Glide.with(this@DetailActivity).load(user.avatarURL).into(binding.imgDetailPhoto)
            binding.imgDetailPhoto.contentDescription = "${user.name}'s photo"
        }

        detailViewModel.getFavoriteUserByUsername(user.login).observe(this) { favoriteUser ->
            if (favoriteUser != null) {
                binding.fabFavorite.setOnClickListener {
                    detailViewModel.delete(favoriteUser)
                    showToast(getString(R.string.removed))
                }
            } else {
                binding.fabFavorite.setOnClickListener {
                    detailViewModel.insert(FavoriteUser(user.login, user.avatarURL))
                    showToast(getString(R.string.added))
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(isError: Boolean) {
        if (isError) Toast.makeText(
            this, "Error displaying user details. Please try again later.", Toast.LENGTH_LONG
        ).show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}