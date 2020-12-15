package com.willyweather.assignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.willyweather.assignment.data.Repository
import com.willyweather.assignment.model.Repo
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: Repository) : ViewModel() {

    fun getRepos() = repository.getSearchResultStream()
        .cachedIn(viewModelScope)

    fun showRepoDetails(it: Repo) {
        
    }

}