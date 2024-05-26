package com.example.kotlingithubclient.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlingithubclient.R
import com.example.kotlingithubclient.adapter.FollowersRecyclerAdapter
import com.example.kotlingithubclient.model.Followers
import com.example.kotlingithubclient.service.FollowersApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FollowersFragment : Fragment() {
    private lateinit var username: String
    private lateinit var type: String

    private val disposable = CompositeDisposable()
    private val followersApiService: FollowersApiService = FollowersApiService()
    private var followers: List<Followers> = emptyList()

    private lateinit var followersScreenUsernameTextView: TextView
    private lateinit var typeTextView: TextView
    private lateinit var backToSelectedScreenImageButton: ImageButton
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_followers, container, false)

        followersScreenUsernameTextView = view.findViewById(R.id.followersScreenUsernameTextView)
        typeTextView = view.findViewById(R.id.typeTextView)
        backToSelectedScreenImageButton = view.findViewById(R.id.backToSelectedScreenImageButton)

        getApiUrl()

        val layoutManager = LinearLayoutManager(context)
        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.followersRecyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = FollowersRecyclerAdapter(followers)
        recyclerView.adapter = adapter

        getFollowers(adapter)

        backToSelectedScreenImageButton.setOnClickListener {
            val action = FollowersFragmentDirections.actionFollowersFragmentToSelectedUserFragment(username)
            Navigation.findNavController(it).navigate(action)
        }

        return view
    }
    private fun getApiUrl() {
        arguments?.let {
            val safeArgs = FollowersFragmentArgs.fromBundle(it)
            username = safeArgs.login
            type = safeArgs.type

            followersScreenUsernameTextView.text = username
            typeTextView.text = type
        }
    }

    private fun getFollowers(adapter: FollowersRecyclerAdapter) {
        disposable.add(
            followersApiService.getFollowers(username, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Followers>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onSuccess(response: List<Followers>) {
                        followers = response
                        adapter.followers = followers
                        adapter.notifyDataSetChanged()
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }
}
