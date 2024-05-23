package com.example.kotlingithubclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlingithubclient.R
import com.example.kotlingithubclient.model.SearchedUser
import com.example.kotlingithubclient.view.SearchUserFragmentDirections

class SearchedUserRecyclerAdapter(var searchedUsers: List<SearchedUser>): RecyclerView.Adapter<SearchedUserRecyclerAdapter.SearchedUserViewHolder>() {
    class SearchedUserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val searchedUsernameTextView: TextView = itemView.findViewById(R.id.searchedUsernameTextView)
        val searchedProfilePhotoImageView: ImageView = itemView.findViewById(R.id.searchedProfilePhotoImageView)
        val searchedUserLinearLayout: View = itemView.findViewById(R.id.searchedUserLinearLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedUserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.searched_user_recycler_row, parent, false)
        return SearchedUserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchedUsers.size
    }

    override fun onBindViewHolder(holder: SearchedUserViewHolder, position: Int) {
        val user = searchedUsers[position]

        holder.searchedUsernameTextView.text = user.login
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .into(holder.searchedProfilePhotoImageView)

        holder.searchedUserLinearLayout.setOnClickListener {
            val action =
                SearchUserFragmentDirections.actionSearchUserFragmentToSelectedUserFragment(
                    searchedUsers[position].login.toString()
                )
            Navigation.findNavController(it).navigate(action)
        }
    }
}