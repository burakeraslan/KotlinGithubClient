package com.example.kotlingithubclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlingithubclient.R
import com.example.kotlingithubclient.model.Followers
import com.example.kotlingithubclient.view.FollowersFragmentDirections

class FollowersRecyclerAdapter(var followers: List<Followers>): RecyclerView.Adapter<FollowersRecyclerAdapter.FollowersViewHolder>() {
    lateinit var followersUsernameTextView: TextView
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
        val followersUsernameTextView: TextView = holder.itemView.findViewById<TextView>(R.id.followersUsernameTextView)
        holder.itemView.apply {
            followersUsernameTextView.text = followers[position].login
        }

        followersUsernameTextView.setOnClickListener {
            val action = FollowersFragmentDirections.actionFollowersFragmentToSelectedUserFragment(
                followers[position].login.toString()
            )
            Navigation.findNavController(it).navigate(action)
        }
    }


}