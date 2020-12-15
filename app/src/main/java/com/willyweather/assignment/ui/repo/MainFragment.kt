package com.willyweather.assignment.ui.repo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.willyweather.assignment.adapter.ReposLoadStateAdapter
import com.willyweather.assignment.R
import com.willyweather.assignment.adapter.ReposAdapter
import com.willyweather.assignment.ui.MainViewModel
import com.willyweather.assignment.ui.details.RepoDetailsFragment
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@ExperimentalCoroutinesApi
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var adapter: ReposAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh?.setOnRefreshListener { adapter.refresh() }

        initAdapter()
        loadRepos()
    }

    private fun initAdapter() {

        adapter = ReposAdapter {
//            viewModel.showRepoDetails(it)
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.container, RepoDetailsFragment.newInstance(it))
                ?.addToBackStack(null)
                ?.commit()
        }

        repo_list?.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { adapter.refresh() },
            footer = ReposLoadStateAdapter { adapter.refresh() }
        )

        lifecycleScope.launchWhenCreated {
            @OptIn(FlowPreview::class)
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { repo_list?.scrollToPosition(0) }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapter.loadStateFlow.collectLatest { loadStates ->
                swipe_refresh?.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }
    }

    private fun loadRepos() {
        lifecycleScope.launch {
            viewModel.getRepos().collectLatest {
                adapter.submitData(it)
            }
        }
    }

}