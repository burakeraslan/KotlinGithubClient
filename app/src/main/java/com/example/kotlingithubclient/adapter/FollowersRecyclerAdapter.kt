package com.example.kotlingithubclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlingithubclient.R
import com.example.kotlingithubclient.model.Followers
import com.example.kotlingithubclient.view.FollowersFragmentDirections

class FollowersRecyclerAdapter(var followers: List<Followers>): RecyclerView.Adapter<FollowersRecyclerAdapter.FollowersViewHolder>() {
    lateinit var followersUsernameTextView: TextView
    class FollowersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val followersUsernameTextView: TextView = itemView.findViewById(R.id.followersUsernameTextView)
        val followersProfilePhotoImageView: ImageView = itemView.findViewById(R.id.followersProfilePhotoImageView)
        val followersLinearLayout: View = itemView.findViewById(R.id.followersLinearLayout)
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
        val followers = followers[position]

        holder.followersUsernameTextView.text = followers.login

        Glide.with(holder.itemView.context)
            .load(followers.avatarUrl)
            .into(holder.followersProfilePhotoImageView)

        holder.followersLinearLayout.setOnClickListener {
            val action = FollowersFragmentDirections.actionFollowersFragmentToSelectedUserFragment(
                followers.login.toString()
            )
            Navigation.findNavController(it).navigate(action)
        }
    }


}