package com.example.kotlingithubclient.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlingithubclient.R
import com.example.kotlingithubclient.adapter.SearchedUserRecyclerAdapter
import com.example.kotlingithubclient.model.SearchedResult
import com.example.kotlingithubclient.model.SearchedUser
import com.example.kotlingithubclient.service.SearchedUserApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SearchUserFragment : Fragment() {
    private lateinit var usernameEditText: EditText
    private lateinit var searchImageButton: ImageButton
    private var searchedUsers: List<SearchedUser> = emptyList()

    private val searchedUserApiService: SearchedUserApiService = SearchedUserApiService()
    private val disposible = CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_user, container, false)

        usernameEditText = view.findViewById(R.id.usernameEditText)
        searchImageButton = view.findViewById(R.id.searchImageButton)

        searchImageButton.setOnClickListener {
            fetchSearchedGithubUsersData(usernameEditText.text.toString(), view.findViewById<androidx.recyclerview.widget.RecyclerView>(
                R.id.searchedUserRecyclerView
            ).adapter as SearchedUserRecyclerAdapter)
        }

        val layoutManager = LinearLayoutManager(context)
        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.searchedUserRecyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = SearchedUserRecyclerAdapter(searchedUsers)
        recyclerView.adapter = adapter

        return view
    }

    private fun fetchSearchedGithubUsersData(username: String, adapter: SearchedUserRecyclerAdapter) {
        disposible.add(
            searchedUserApiService.getUsers(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SearchedResult>() {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onSuccess(searchedResult: SearchedResult) {
                        searchedUsers = searchedResult.items ?: emptyList()
                        adapter.searchedUsers = searchedUsers
                        adapter.notifyDataSetChanged()
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
