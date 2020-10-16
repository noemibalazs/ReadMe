package com.noemi.android.readme

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.noemi.android.readme.data.RepositoryDetails
import com.noemi.android.readme.data.RepositoryReadMe
import com.noemi.android.readme.details.RepositoryDetailsActivity
import com.noemi.android.readme.util.setSpannableText
import kotlinx.android.synthetic.main.activity_repository_details.*
import kotlinx.android.synthetic.main.repository_readme.view.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class RepositoryDetailsActivityInstrumentationTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(RepositoryDetailsActivity::class.java)

    @Test
    fun layoutItemsAreVisibleTest() {
        val context = activityTestRule.activity.applicationContext
        val activity = activityTestRule.activity
        val repositoryDetails = RepositoryDetails(
            id = 12, name = "Anko", description = "That's all", size = 33, forks_count = 12,
            stargazers_count = 21, open_issues_count = 27, watchers_count = 69
        )
        val repoReadMe = RepositoryReadMe(
            "Try to decode me... using Base64."
        )

        activityTestRule.activity.runOnUiThread {
            activity.tv_repo_name.setSpannableText(
                context.getString(
                    R.string.txt_repo_name,
                    repositoryDetails.name
                )
            )
            activity.tv_repo_description.setSpannableText(
                context.getString(
                    R.string.txt_repo_description,
                    repositoryDetails.description
                )
            )
            activity.tv_repo_size.setSpannableText(
                context.getString(
                    R.string.txt_repo_size,
                    repositoryDetails.size
                )
            )
            activity.tv_repo_watchers.setSpannableText(
                context.getString(
                    R.string.txt_repo_watchers,
                    repositoryDetails.watchers_count
                )
            )
            activity.tv_repo_stargazers.setSpannableText(
                context.getString(
                    R.string.txt_repo_stargazers,
                    repositoryDetails.stargazers_count
                )
            )
            activity.tv_repo_forks.setSpannableText(
                context.getString(
                    R.string.txt_repo_forks,
                    repositoryDetails.forks_count
                )
            )
            activity.tv_repo_open_issues_count.setSpannableText(
                context.getString(
                    R.string.txt_repo_open_issues_count,
                    repositoryDetails.open_issues_count
                )
            )

            activity.cl_read_me.tv_readme_details.text = repoReadMe.content
        }

        onView(withId(R.id.tv_repo_name))
            .check(matches(isDisplayed())).check(
                matches(
                    withText(
                        context.getString(
                            R.string.txt_repo_name,
                            repositoryDetails.name
                        )
                    )
                )
            )
        onView(withId(R.id.tv_repo_description))
            .check(matches(isDisplayed())).check(
                matches(
                    withText(
                        context.getString(
                            R.string.txt_repo_description,
                            repositoryDetails.description
                        )
                    )
                )
            )
        onView(withId(R.id.tv_repo_size))
            .check(matches(isDisplayed())).check(
                matches(
                    withText(
                        context.getString(
                            R.string.txt_repo_size,
                            repositoryDetails.size
                        )
                    )
                )
            )
        onView(withId(R.id.tv_repo_stargazers))
            .check(matches(isDisplayed())).check(
                matches(
                    withText(
                        context.getString(
                            R.string.txt_repo_stargazers,
                            repositoryDetails.stargazers_count
                        )
                    )
                )
            )
        onView(withId(R.id.tv_repo_watchers))
            .check(matches(isDisplayed())).check(
                matches(
                    withText(
                        context.getString(
                            R.string.txt_repo_watchers,
                            repositoryDetails.watchers_count
                        )
                    )
                )
            )
        onView(withId(R.id.tv_repo_forks))
            .check(matches(isDisplayed())).check(
                matches(
                    withText(
                        context.getString(
                            R.string.txt_repo_forks,
                            repositoryDetails.forks_count
                        )
                    )
                )
            )
        onView(withId(R.id.tv_repo_open_issues_count))
            .check(matches(isDisplayed())).check(
                matches(
                    withText(
                        context.getString(
                            R.string.txt_repo_open_issues_count,
                            repositoryDetails.open_issues_count
                        )
                    )
                )
            )

        onView(withId(R.id.tv_readme_details)).check(matches(isDisplayed())).check(
            matches(withText(repoReadMe.content))
        )
    }
}