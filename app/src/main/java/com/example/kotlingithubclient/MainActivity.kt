package com.example.kotlingithubclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.kotlingithubclient.model.SearchedResult
import com.example.kotlingithubclient.model.SearchedUser
import com.example.kotlingithubclient.service.SearchedUserApiService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var textView: TextView

    val searchedUserApiService: SearchedUserApiService = SearchedUserApiService()
    val disposible = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        searchButton = findViewById<Button>(R.id.searchButton)
        textView = findViewById<TextView>(R.id.textView)

        searchButton.setOnClickListener {
            searchUser()
            fetchSearchedGithubUsersData(usernameEditText.text.toString())
        }

    }

    fun searchUser() {
        val username = usernameEditText.text.toString()
        textView.text = "Searching for $username"
    }
    fun fetchSearchedGithubUsersData(username: String) {
        disposible.add(
            searchedUserApiService.getUsers(username)
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableSingleObserver<SearchedResult>() {
                    override fun onSuccess(searchedResult: SearchedResult) {
                        val searchedUsers: List<SearchedUser>? = searchedResult.items
                        if (searchedUsers != null) {
                            for (searchedUser in searchedUsers) {
                                println(searchedUser.login)
                            }
                        }
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