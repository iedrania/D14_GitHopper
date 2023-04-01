package com.iedrania.githopper.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iedrania.githopper.database.remote.response.UserResponse
import com.iedrania.githopper.databinding.ItemUserBinding
import com.iedrania.githopper.ui.detail.DetailActivity

class UserAdapter(private val listUser: List<UserResponse>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]
        holder.binding.tvItemUsername.text = user.login
        Glide.with(holder.binding.imgItemPhoto).load(user.avatarURL)
            .into(holder.binding.imgItemPhoto)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("extra_user", user)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount() = listUser.size

    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}