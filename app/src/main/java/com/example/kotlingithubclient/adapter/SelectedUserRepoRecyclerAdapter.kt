package com.example.kotlingithubclient.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlingithubclient.R
import com.example.kotlingithubclient.model.SelectedUserRepo

class SelectedUserRepoRecyclerAdapter(var selectedUserRepos: List<SelectedUserRepo>) : RecyclerView.Adapter<SelectedUserRepoRecyclerAdapter.SelectedUserRepoViewHolder>() {
    class SelectedUserRepoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val selectedUserRepoNameTextView: TextView = itemView.findViewById(R.id.selectedUserRepoNameTextView)
        val selectedUserRepoLanguageTextView: TextView = itemView.findViewById(R.id.selectedUserRepoLanguageTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedUserRepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.selected_user_repo_recycler_row, parent, false)
        return SelectedUserRepoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return selectedUserRepos.size
    }

    override fun onBindViewHolder(holder: SelectedUserRepoViewHolder, position: Int) {
        val userRepo = selectedUserRepos[position]

        holder.selectedUserRepoNameTextView.text = userRepo.name
        holder.selectedUserRepoLanguageTextView.text = userRepo.language
    }
}