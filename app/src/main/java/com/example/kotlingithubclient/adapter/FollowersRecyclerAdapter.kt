package com.example.kotlingithubclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlingithubclient.R
import com.example.kotlingithubclient.model.Followers

class FollowersRecyclerAdapter(var followers: List<Followers>): RecyclerView.Adapter<FollowersRecyclerAdapter.FollowersViewHolder>() {
    class FollowersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.followers_recycler_row, parent, false)
        return FollowersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return followers.size
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.itemView.apply {
             findViewById<TextView>(R.id.followersUsernameTextView).text = followers[position].login
        }
    }


}