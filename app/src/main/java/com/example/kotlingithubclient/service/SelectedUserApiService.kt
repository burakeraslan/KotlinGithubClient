package com.example.kotlingithubclient.service

import com.example.kotlingithubclient.model.SelectedUser
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SelectedUserApiService {
    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(SelectedUserApi::class.java)

    fun getUser(username: String): Single<SelectedUser> {
        return retrofit.getUser(username)
    }
}