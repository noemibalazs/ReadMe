package com.noemi.android.readme.repository

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.noemi.android.readme.R
import com.noemi.android.readme.data.Repository
import com.noemi.android.readme.databinding.ActivityRepositoryBinding
import com.noemi.android.readme.details.RepositoryDetailsActivity
import com.noemi.android.readme.helper.DataManager
import com.noemi.android.readme.helper.OnClickEvent
import com.noemi.android.readme.helper.RepositoryClickListener
import com.noemi.android.readme.util.SAVED_INSTANCE_KEY
import com.noemi.android.readme.util.openActivity
import org.koin.android.ext.android.inject

class RepositoryActivity : AppCompatActivity() {

    private val repositoryViewModel: RepositoryViewModel by inject()
    private val dataManager: DataManager by inject()
    private lateinit var binding: ActivityRepositoryBinding
    private lateinit var adapter: RepositoryAdapter

    private val repositoryClickListener = object : RepositoryClickListener {
        override fun onRepositoryClicked(id: Int, fullName: String) {
            dataManager.saveRepositoryId(id)
            dataManager.saveRepositoryFullName(fullName)
            openActivity(RepositoryDetailsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repository)

        initBinding()
        initObservers()

        if (savedInstanceState != null) {
            val repos = savedInstanceState.getParcelableArrayList<Repository>(SAVED_INSTANCE_KEY)
            adapter.submitList(repos)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val list = adapter.currentList
        val repos = arrayListOf<Repository>()
        repos.addAll(list)
        outState.putParcelableArrayList(SAVED_INSTANCE_KEY, repos)
        super.onSaveInstanceState(outState)
    }

    private fun initBinding() {
        binding.viewModel = repositoryViewModel
        adapter = RepositoryAdapter(repositoryViewModel).apply {
            this.repoClickListener = repositoryClickListener
        }
        binding.rvRepository.adapter = adapter
    }

    private fun initObservers() {
        repositoryViewModel.searchingOK.observe(this, {
            loadRepositories(it)
        })

        repositoryViewModel.searchingError.observe(this, {
            searchingError(it)
        })

        repositoryViewModel.emptyOrShortNameError.observe(this, {
            showErrorToastToUser(getString(R.string.txt_empty_search))
        })

        repositoryViewModel.mutableRepositories.observe(this, {
            adapter.submitList(it)
            hideSoftKeyBoard()
        })

        repositoryViewModel.loading.observe(this, {
            binding.pbRepository.visibility = if (it) View.VISIBLE else View.GONE
        })

        repositoryViewModel.failureError.observe(this, {
            showErrorToastToUser(getString(R.string.txt_error))
        })
    }

    private fun loadRepositories(event: OnClickEvent) {
        if (event == OnClickEvent.SEARCHING_OK)
            repositoryViewModel.initSearching()
    }

    private fun searchingError(event: OnClickEvent) {
        if (event == OnClickEvent.SEARCHING_ERROR)
            repositoryViewModel.searchingError()
    }

    private fun hideSoftKeyBoard() {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.etSearchByName.windowToken, 0)
    }

    private fun showErrorToastToUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.removeListener()
    }
}