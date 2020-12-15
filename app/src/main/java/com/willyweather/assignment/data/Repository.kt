package com.willyweather.assignment.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.willyweather.assignment.data.local.GithubDb
import com.willyweather.assignment.data.remote.GithubService

class Repository(private val db: GithubDb, private val service: GithubService) {

    fun getSearchResultStream() = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
        remoteMediator = GithubPagingSource(db, service)
    ) {
        db.repoDao().repos()
    }.flow

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}
