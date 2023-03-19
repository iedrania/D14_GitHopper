package com.iedrania.githopper

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.iedrania.githopper.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers_tab_text,
            R.string.following_tab_text
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ViewModel::class.java]
        viewModel.user.observe(this) { user ->
            setUserDetails(user)
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        viewModel.isError.observe(this) {
            showError(it)
        }
        val username = intent.getStringExtra(EXTRA_USERNAME)!!
        viewModel.getUserDetail(username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
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
            1 -> "${user.followers} Follower · ${user.following} Following".also { binding.tvDetailStats.text = it }
            else -> "${user.followers} Followers · ${user.following} Following".also { binding.tvDetailStats.text = it }
        }

        if (user.avatarURL == null) {
            binding.imgDetailPhoto.visibility = View.GONE
        } else {
            Glide.with(this@DetailActivity)
                .load(user.avatarURL)
                .into(binding.imgDetailPhoto)
            binding.imgDetailPhoto.contentDescription = "${user.name}'s photo"
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(isError: Boolean) {
        if (isError) Toast.makeText(this, "Error displaying user details. Please try again later.", Toast.LENGTH_LONG).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}