package com.example.kotlingithubclient.service

import com.example.kotlingithubclient.model.SelectedUserRepo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface SelectedUserReposApi {
    @GET
    fun getRepos(@Url url: String): Single<List<SelectedUserRepo>>
}