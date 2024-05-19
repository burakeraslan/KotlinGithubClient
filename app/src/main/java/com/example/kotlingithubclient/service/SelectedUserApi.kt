package com.example.kotlingithubclient.service

import com.example.kotlingithubclient.model.SelectedUser
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface SelectedUserApi {
    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Single<SelectedUser>
}
