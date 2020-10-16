package com.noemi.android.readme.repository

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.noemi.android.readme.R
import com.noemi.android.readme.data.Repository
import com.noemi.android.readme.databinding.ItemRepositoryBinding
import com.noemi.android.readme.helper.DebounceClickListener
import com.noemi.android.readme.helper.RepositoryClickListener
import com.noemi.android.readme.util.setSpannableText

class RepositoryVH(
    private val binding: ItemRepositoryBinding,
    private val repositoryViewModel: RepositoryViewModel,
    private val repositoryClickListener: RepositoryClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(repository: Repository) {
        binding.viewModel = repositoryViewModel
        binding.apply {
            val context = binding.root.context
            tvRepoName.setSpannableText(
                context.getString(R.string.txt_repo_name, repository.name)
            )
            tvRepoDescription.setSpannableText(
                context.getString(R.string.txt_repo_description, repository.description)
            )

            cvRepoContainer.setOnClickListener(object : DebounceClickListener() {
                override fun onDebounce(view: View) {
                    repositoryClickListener?.onRepositoryClicked(
                        repository.id,
                        repository.full_name ?: ""
                    )
                }
            })
        }
    }
}