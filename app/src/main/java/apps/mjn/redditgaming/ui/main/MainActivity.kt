package apps.mjn.redditgaming.ui.main

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import apps.mjn.domain.entity.RedditPostItem
import apps.mjn.domain.entity.RedditPostListItem
import apps.mjn.redditgaming.ARG_LIST
import apps.mjn.redditgaming.R
import apps.mjn.redditgaming.extension.createViewModel
import apps.mjn.redditgaming.extension.observe
import apps.mjn.redditgaming.ui.base.BaseActivity
import apps.mjn.redditgaming.ui.main.adapter.PostAdapter
import apps.mjn.redditgaming.ui.main.adapter.VerticalSpaceItemDecoration
import apps.mjn.redditgaming.ui.model.Resource
import apps.mjn.redditgaming.ui.model.ResourceState
import apps.mjn.redditgaming.ui.viewmodel.GamingListViewModel
import apps.mjn.redditgaming.util.recyclerview.InfiniteLinearScrollListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.net.Uri
import java.lang.Exception


class MainActivity : BaseActivity() {

    private lateinit var viewModel: GamingListViewModel
    private var nextPageTag: String = ""
    private var postAdapter = PostAdapter(ArrayList()) {
        onPostClick(it)
    }
    private val loadMoreThreshold = 10
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = createViewModel(viewModelFactory) {
            observe(getData(), ::handleStates)
        }
        initList()
    }

    private fun initList() {
        rvPosts.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.space_0_5x)))
        val list = intent.getParcelableExtra(ARG_LIST) as RedditPostListItem?
        rvPosts.adapter = postAdapter
        list?.let { postItem ->
            nextPageTag = postItem.data?.nextPageTag ?: ""
            addToList(postItem.data?.posts?.mapNotNull { it.data })
        } ?: loadMore()
        rvPosts.addOnScrollListener(InfiniteLinearScrollListener(loadMoreThreshold, rvPosts.layoutManager as LinearLayoutManager) {
            if(!isLoading) {
                loadMore()
            }
        })
    }

    private fun addToList(items: List<RedditPostItem>?) {
        postAdapter.addItems(items)
    }

    private fun loadMore() {
        isLoading = true
        viewModel.load(nextPageTag)
    }

    private fun onPostClick(redditPostItem: RedditPostItem) {
        openPostInBrowser(redditPostItem.permanentLink)
    }

    private fun openPostInBrowser(link: String?) {
        val https = "https://"
        val http = "http://"
        var finalLink = link
        link?.let {
            if (!link.startsWith(http) && !link.startsWith(https)) {
                finalLink = http + link
            }
            var intent: Intent? = null
            try {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(finalLink))
            } catch (e: Exception) {
                Toast.makeText(this, getString(R.string.link_not_correct), Toast.LENGTH_LONG).show()
            }
            if (intent?.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.browser_not_found), Toast.LENGTH_LONG).show()
            }
        } ?: Toast.makeText(this, getString(R.string.link_not_found), Toast.LENGTH_LONG).show()
    }

    private fun handleStates(resource: Resource<RedditPostListItem>?) {
        resource?.let {
            when (resource.resourceState) {
                ResourceState.LOADING -> showLoading()
                ResourceState.SUCCESS -> handleSuccess(resource.data!!)
                ResourceState.ERROR -> handleError(resource.failure!!)
            }
        }
    }

    private fun showLoading() {
        postAdapter.showLoading()
    }

    private fun hideLoading() {
        postAdapter.hideLoading()
    }

    private fun handleSuccess(list: RedditPostListItem) {
        isLoading = false
        hideLoading()
        addToList(list.data?.posts?.mapNotNull { it.data })
        nextPageTag = list.data?.nextPageTag ?: ""
    }

    private fun handleError(failure: Throwable) {
        isLoading = false
        hideLoading()
        showSnackBar(failure.message) { loadMore() }
    }

    private fun showSnackBar(message: String?, action: () -> Unit) {
        message?.let {
            Snackbar.make(parentLayout, message, Snackbar.LENGTH_INDEFINITE).apply {
                view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams).apply {
                    setAction(getString(R.string.try_again)) {
                        action()
                    }
                }
            }.show()
        }
    }
}
