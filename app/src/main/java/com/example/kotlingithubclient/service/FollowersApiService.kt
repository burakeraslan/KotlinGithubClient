package com.example.kotlingithubclient.service

import com.example.kotlingithubclient.model.Followers
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FollowersApiService {
    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(FollowersApi::class.java)

    fun getFollowers(username: String, type: String): Single<List<Followers>> {
        return retrofit.getFollowers(username, type)
    }
}