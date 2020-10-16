package com.noemi.android.readme.details

import android.nfc.FormatException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.noemi.android.readme.R
import com.noemi.android.readme.data.RepositoryDetails
import com.noemi.android.readme.data.RepositoryReadMe
import com.noemi.android.readme.databinding.ActivityRepositoryDetailsBinding
import com.noemi.android.readme.util.REPOSITORY_DETAILS_SAVED_STATE
import com.noemi.android.readme.util.REPOSITORY_README_SAVED_STATE
import com.noemi.android.readme.util.setSpannableText
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.logger.KOIN_TAG
import java.nio.charset.StandardCharsets

class RepositoryDetailsActivity : AppCompatActivity() {

    private val repoDetailsViewModel: RepositoryDetailsViewModel by stateViewModel()
    private lateinit var binding: ActivityRepositoryDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repository_details)

        initBinding()
        initObservers()
    }

    private fun initBinding() {
        binding.viewModel = repoDetailsViewModel
    }

    private fun initObservers() {
        repoDetailsViewModel.showDetailsError.observe(this, {
            showErrorToastToUser(getString(R.string.txt_error))
        })

        repoDetailsViewModel.showReadmeError.observe(this, {
            showErrorToastToUser(getString(R.string.txt_error))
        })

        val detailsSavedState = repoDetailsViewModel.handle.contains(REPOSITORY_DETAILS_SAVED_STATE)

        if (!detailsSavedState) {
            repoDetailsViewModel.getRepositoryDetails().observe(this, {
                populateUI(it)
                repoDetailsViewModel.saveRepositoryDetailsDueConfChanges(it)
            })
        } else {
            repoDetailsViewModel.getRepositoryDetailsAfterConfChanges().observe(this, {
                populateUI(it)
            })
        }

        val readmeSavedState = repoDetailsViewModel.handle.contains(REPOSITORY_README_SAVED_STATE)

        if (!readmeSavedState) {
            repoDetailsViewModel.getRepositoryReadme().observe(this, {
                populateReadme(it)
                repoDetailsViewModel.saveRepositoryReadmeDueConfChanges(it)
            })
        } else {
            repoDetailsViewModel.getRepositoryReadmeAfterConfChanges().observe(this, {
                populateReadme(it)
            })
        }
    }

    private fun showErrorToastToUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun populateUI(repositoryDetails: RepositoryDetails) {
        Log.d("DETAILS" , " RD: $repositoryDetails")
        binding.tvRepoName.setSpannableText(
            getString(
                R.string.txt_repo_name,
                repositoryDetails.name
            )
        )
        binding.tvRepoDescription.setSpannableText(
            getString(
                R.string.txt_repo_description,
                repositoryDetails.description
            )
        )
        binding.tvRepoSize.setSpannableText(
            getString(
                R.string.txt_repo_size,
                repositoryDetails.size
            )
        )
        binding.tvRepoWatchers.setSpannableText(
            getString(
                R.string.txt_repo_watchers,
                repositoryDetails.watchers_count
            )
        )
        binding.tvRepoStargazers.setSpannableText(
            getString(
                R.string.txt_repo_stargazers,
                repositoryDetails.stargazers_count
            )
        )
        binding.tvRepoForks.setSpannableText(
            getString(
                R.string.txt_repo_forks,
                repositoryDetails.forks_count
            )
        )
        binding.tvRepoOpenIssuesCount.setSpannableText(
            getString(
                R.string.txt_repo_open_issues_count,
                repositoryDetails.open_issues_count
            )
        )
    }

    private fun populateReadme(readMe: RepositoryReadMe) {
        val content = readMe.content

        content?.let {
            try {
                val decodedReadme: ByteArray = Base64.decode(it, Base64.NO_PADDING)
                val readMeText = String(decodedReadme, StandardCharsets.UTF_8)
                binding.tvRepoDescription.text = readMeText
                Log.d("DETAILS", "TEXT: $readMeText")

            } catch (e: FormatException) {
                Log.e(KOIN_TAG, "Exception thrown at decoding.")
            }
        }
    }
}