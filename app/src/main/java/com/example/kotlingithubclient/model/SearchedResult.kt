package com.example.kotlingithubclient.model

import com.google.gson.annotations.SerializedName

data class SearchedResult(
    @SerializedName("total_count")
    val total_count: Int?,
    @SerializedName("incomplete_results")
    val incomplete_results: Boolean?,
    @SerializedName("items")
    val items: List<SearchedUser>?
) {
}