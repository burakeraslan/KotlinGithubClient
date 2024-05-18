package com.example.kotlingithubclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlingithubclient.R
import com.example.kotlingithubclient.model.SearchedUser

class SearchedUserRecyclerAdapter(var searchedUsers: List<SearchedUser>): RecyclerView.Adapter<SearchedUserRecyclerAdapter.SearchedUserViewHolder>() {
    class SearchedUserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

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
        holder.itemView.apply {
            findViewById<TextView>(R.id.searchedUsernameTextView).text = searchedUsers[position].login
        }
    }
}