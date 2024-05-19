package com.example.kotlingithubclient

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kotlingithubclient.adapter.SearchedUserRecyclerAdapter
import com.example.kotlingithubclient.model.SearchedResult
import com.example.kotlingithubclient.model.SelectedUser
import com.example.kotlingithubclient.service.SelectedUserApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SelectedUserFragment : Fragment() {
    private lateinit var selectedUsername: String

    private val disposible = CompositeDisposable()
    private val selectedUserApiService: SelectedUserApiService = SelectedUserApiService()

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
        getSelectedUser()
        fetchSelectedUser(selectedUsername)
        return view
    }

    private fun getSelectedUser() {
        arguments?.let {
            val safeArgs = SelectedUserFragmentArgs.fromBundle(it)
            selectedUsername = safeArgs.login
        }
    }

    private fun fetchSelectedUser(selectedUsername: String) {
        disposible.add(
            selectedUserApiService.getUser(selectedUsername)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SelectedUser>() {
                    override fun onSuccess(selectedUser: SelectedUser) {
                        selectedFollowersTextView = view!!.findViewById(R.id.selectedFollowersTextView)
                        selectedFollowingTextView = view!!.findViewById(R.id.selectedFollowingTextView)
                        selectedNameTextView = view!!.findViewById(R.id.selectedNameTextView)
                        selectedReposTextView = view!!.findViewById(R.id.selectedReposTextView)
                        selectedUsernameTextView = view!!.findViewById(R.id.selectedUsernameTextView)

                        selectedUsernameTextView.text = "Username: ${selectedUsername.toString()}"
                        selectedNameTextView.text = "Name: ${selectedUser.name.toString()}"
                        selectedReposTextView.text = "Public Repos: ${selectedUser.publicRepos.toString()}"
                        selectedFollowersTextView.text = "Followers: ${selectedUser.followers.toString()}"
                        selectedFollowingTextView.text = "Following: ${selectedUser.following.toString()}"
                    }
                    override fun onError(e: Throwable) {
                        println(
                            e.message
                        )
                    }
                })
        )
    }

}