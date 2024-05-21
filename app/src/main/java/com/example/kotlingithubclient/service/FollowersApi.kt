package com.example.kotlingithubclient.service

import com.example.kotlingithubclient.model.Followers
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface FollowersApi {
    @GET("users/{username}/{type}")
    fun getFollowers(
        @Path("username") username: String,
        @Path("type") type: String
    ): Single<List<Followers>>
}