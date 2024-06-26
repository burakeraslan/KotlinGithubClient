package com.example.kotlingithubclient.service

import SearchedUserApi
import com.example.kotlingithubclient.model.SearchedResult
import com.example.kotlingithubclient.model.SearchedUser
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SearchedUserApiService {
    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(SearchedUserApi::class.java)

    fun getUsers(username: String): Single<SearchedResult> {
        return retrofit.getUsers(username)
    }
}