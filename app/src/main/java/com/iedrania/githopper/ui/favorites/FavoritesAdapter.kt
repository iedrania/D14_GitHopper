package com.iedrania.githopper.ui.favorites

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iedrania.githopper.UserResponse
import com.iedrania.githopper.database.FavoriteUser
import com.iedrania.githopper.databinding.ItemUserBinding
import com.iedrania.githopper.helper.FavoriteUserDiffCallback
import com.iedrania.githopper.ui.detail.DetailActivity

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoriteUserViewHolder>() {
    private val listFavoriteUsers = ArrayList<FavoriteUser>()
    fun setListFavoriteUsers(listFavoriteUsers: List<FavoriteUser>) {
        val diffCallback = FavoriteUserDiffCallback(this.listFavoriteUsers, listFavoriteUsers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteUsers.clear()
        this.listFavoriteUsers.addAll(listFavoriteUsers)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        holder.bind(listFavoriteUsers[position])
    }

    override fun getItemCount(): Int {
        return listFavoriteUsers.size
    }

    inner class FavoriteUserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {
            with(binding) {
                tvItemUsername.text = favoriteUser.username
                Glide.with(imgItemPhoto).load(favoriteUser.avatarUrl).into(imgItemPhoto)
                itemView.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(
                        "extra_user", UserResponse(
                            login = favoriteUser.username,
                            avatarURL = favoriteUser.avatarUrl,
                            null,
                            null,
                            null
                        )
                    )
                    it.context.startActivity(intent)
                }
            }
        }
    }
}