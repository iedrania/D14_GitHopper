package com.iedrania.githopper.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iedrania.githopper.R
import com.iedrania.githopper.UserAdapter
import com.iedrania.githopper.UserResponse
import com.iedrania.githopper.databinding.ActivityMainBinding
import com.iedrania.githopper.helper.ViewModelFactory
import com.iedrania.githopper.ui.favorites.FavoritesActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = obtainViewModel(this@MainActivity)
        mainViewModel.listUser.observe(this) { listUser ->
            setUsersData(listUser)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.isError.observe(this) {
            showError(it)
        }

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    private fun setUsersData(users: List<UserResponse>) {
        val listUser = ArrayList<UserResponse>()
        for (user in users) {
            listUser.add(
                UserResponse(user.login, user.avatarURL, user.name, user.followers, user.following)
            )
        }
        if (listUser.isEmpty()) {
            binding.searchHint.text = getString(R.string.no_users)
            binding.rvUsers.adapter = null
        } else {
            binding.searchHint.visibility = View.GONE
            binding.rvUsers.adapter = UserAdapter(listUser)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.searchHint.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(isError: Boolean) {
        if (isError) Toast.makeText(
            this, "Error displaying search results. Please try again later.", Toast.LENGTH_LONG
        ).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let { mainViewModel.findUsers(it) }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorites -> {
                val intent = Intent(this@MainActivity, FavoritesActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}