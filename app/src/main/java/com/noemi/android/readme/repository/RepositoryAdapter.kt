package com.noemi.android.readme.repository

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.noemi.android.readme.R
import com.noemi.android.readme.data.Repository
import com.noemi.android.readme.databinding.ItemRepositoryBinding
import com.noemi.android.readme.helper.RepositoryClickListener

class RepositoryAdapter(
    private val repositoryViewModel: RepositoryViewModel
) : ListAdapter<Repository, RepositoryVH>(RepositoryDiffUtil()) {

    var repoClickListener: RepositoryClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryVH {
        val binding: ItemRepositoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_repository, parent, false
        )
        return RepositoryVH(binding, repositoryViewModel, repoClickListener)
    }

    override fun onBindViewHolder(holder: RepositoryVH, position: Int) {
        holder.onBind(getItem(position))
    }

    fun removeListener() {
        repoClickListener = null
    }
}