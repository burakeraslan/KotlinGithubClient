package com.example.kotlingithubclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlingithubclient.adapter.SearchedUserRecyclerAdapter
import com.example.kotlingithubclient.adapter.SelectedUserRepoRecyclerAdapter
import com.example.kotlingithubclient.model.SelectedUser
import com.example.kotlingithubclient.model.SelectedUserRepo
import com.example.kotlingithubclient.service.SelectedUserApiService
import com.example.kotlingithubclient.service.SelectedUserReposApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SelectedUserFragment : Fragment() {
    private lateinit var selectedUsername: String
    private var selectedUserRepo: List<SelectedUserRepo> = emptyList()

    private val disposable = CompositeDisposable()
    private val selectedUserApiService: SelectedUserApiService = SelectedUserApiService()
    private val selectedUserReposApiService: SelectedUserReposApiService = SelectedUserReposApiService()

    private lateinit var selectedUsernameTextView: TextView
    private lateinit var selectedNameTextView: TextView
    private lateinit var selectedReposTextView: TextView
    private lateinit var selectedFollowersTextView: TextView
    private lateinit var selectedFollowingTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_selected_user, container, false)

        selectedFollowersTextView = view.findViewById(R.id.selectedFollowersTextView)
        selectedFollowingTextView = view.findViewById(R.id.selectedFollowingTextView)
        selectedNameTextView = view.findViewById(R.id.selectedNameTextView)
        selectedReposTextView = view.findViewById(R.id.selectedReposTextView)
        selectedUsernameTextView = view.findViewById(R.id.selectedUsernameTextView)

        getSelectedUser()
        fetchSelectedUser(selectedUsername)

        val layoutManager = LinearLayoutManager(context)
        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.selectedUserRepoRecyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = SelectedUserRepoRecyclerAdapter(selectedUserRepo)
        recyclerView.adapter = adapter

        return view
    }

    private fun getSelectedUser() {
        arguments?.let {
            val safeArgs = SelectedUserFragmentArgs.fromBundle(it)
            selectedUsername = safeArgs.login
        }
    }

    private fun fetchSelectedUser(selectedUsername: String) {
        disposable.add(
            selectedUserApiService.getUser(selectedUsername)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SelectedUser>() {
                    override fun onSuccess(selectedUser: SelectedUser) {
                        selectedUsernameTextView.text = "Username: ${selectedUsername}"
                        selectedNameTextView.text = "Name: ${selectedUser.name ?: "N/A"}"
                        selectedReposTextView.text = "Public Repos: ${selectedUser.publicRepos}"
                        selectedFollowersTextView.text = "Followers: ${selectedUser.followers}"
                        selectedFollowingTextView.text = "Following: ${selectedUser.following}"

                        fetchSelectedUserRepos(selectedUser.reposUrl.toString(), view?.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.selectedUserRepoRecyclerView)?.adapter as SelectedUserRepoRecyclerAdapter)
                    }

                    override fun onError(e: Throwable) {
                        println(e.message)
                    }
                })
        )
    }

    private fun fetchSelectedUserRepos(selectedReposUrl: String, adapter: SelectedUserRepoRecyclerAdapter) {
        disposable.add(
            selectedUserReposApiService.getRepos(selectedReposUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<SelectedUserRepo>>() {
                    override fun onSuccess(selectedUserRepos: List<SelectedUserRepo>) {
                        selectedUserRepo = selectedUserRepos
                        adapter.selectedUserRepos = selectedUserRepo
                        adapter.notifyDataSetChanged()
                    }

                    override fun onError(e: Throwable) {
                        println(e.message)
                    }
                })
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }
}
