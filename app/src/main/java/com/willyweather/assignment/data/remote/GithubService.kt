package com.willyweather.assignment.data.remote

import com.willyweather.assignment.model.Repo
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("orgs/octokit/repos")
    suspend fun repos(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): List<Repo>
}