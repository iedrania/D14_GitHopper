package com.iedrania.githopper

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iedrania.githopper.databinding.ItemUserBinding

class UserAdapter(private val listUser: List<UserResponse>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (login, avatar_url, name, followers, following) = listUser[position]
        holder.binding.tvItemUsername.text = login
        Glide.with(holder.binding.imgItemPhoto)
            .load(avatar_url)
            .into(holder.binding.imgItemPhoto)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("extra_username", login)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount() = listUser.size

    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}