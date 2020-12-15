package com.willyweather.assignment.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.willyweather.assignment.R
import com.willyweather.assignment.model.Repo
import kotlinx.android.synthetic.main.repo_details_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RepoDetailsFragment : Fragment() {

    companion object {

        private const val ARG_REPO = "repo"

        fun newInstance(repo: Repo) = RepoDetailsFragment().apply {
            val args = Bundle()
            args.putParcelable(ARG_REPO, repo)
            arguments = args
        }
    }

    private lateinit var repo: Repo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repo = arguments?.getParcelable(ARG_REPO) as? Repo ?: throw IllegalArgumentException("Need parcelable instance of repo")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.repo_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repo_name?.text = repo.name

        repo_description?.isVisible = repo.description?.isNotBlank() ?: true
        if (repo.description != null) {
            repo_description?.text = repo.description
        }

        val license = repo.license
        if (license != null) {
            repo_license?.visibility = View.VISIBLE
            repo_license?.text = license.name
        } else {
            repo_license?.visibility = View.GONE
        }

        repo_open_issue_count?.text = repo.openIssuesCount.toString()

        val permissions = repo.permissions
        if (permissions != null) {

            repo_permissions?.visibility = View.VISIBLE

            repo_permission_admin?.isVisible = permissions.admin
            repo_permission_push?.isVisible = permissions.push
            repo_permission_pull?.isVisible = permissions.pull

        } else {
            repo_permissions?.visibility = View.GONE
        }

    }
}