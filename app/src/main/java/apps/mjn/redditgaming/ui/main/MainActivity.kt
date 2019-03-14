package apps.mjn.redditgaming.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import apps.mjn.domain.entity.RedditPostItem
import apps.mjn.domain.entity.RedditPostListItem
import apps.mjn.redditgaming.ARG_LIST
import apps.mjn.redditgaming.ARG_LIST_POSITION_X
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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: GamingListViewModel
    private lateinit var redditPostListItem: RedditPostListItem
    private var nextPageTag: String = ""
    private val loadMoreThreshold = 10
    private var isLoading = false
    private var isFailed = false
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = createViewModel(viewModelFactory) {
            observe(getData(), ::handleStates)
        }
        initAdapter()
        initList()
    }

    private fun initAdapter(){
        postAdapter = PostAdapter(
            ArrayList(),
            {
                onPostClick(it)
            },
            {
                hideFailed()
                loadMore()
            }
        )
    }

    private fun initList() {
        rvPosts.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.space_0_5x)))
        val list = intent.getParcelableExtra(ARG_LIST) as RedditPostListItem?
        val listScrollPosition = intent.getIntExtra(ARG_LIST_POSITION_X, 0)
        rvPosts.adapter = postAdapter
        list?.let { postItem ->
            redditPostListItem = list
            nextPageTag = postItem.data?.nextPageTag ?: ""
            addToList(postItem.data?.posts?.mapNotNull { it.data })
            rvPosts.scrollTo(listScrollPosition, 0)
        } ?: loadMore()
        rvPosts.addOnScrollListener(
            InfiniteLinearScrollListener(
                loadMoreThreshold,
                rvPosts.layoutManager as LinearLayoutManager) {
                if (!isLoading && !isFailed) {
                    loadMore()
                }
            })
    }

    private fun addToList(items: List<RedditPostItem>?) {
        postAdapter.addItems(items)
    }

    private fun loadMore() {
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
        isLoading = true
        postAdapter.showLoading()
    }

    private fun hideLoading() {
        isLoading = false
        postAdapter.hideLoading()
    }

    private fun handleSuccess(list: RedditPostListItem) {
        hideLoading()
        addToList(list.data?.posts?.mapNotNull { it.data })
        nextPageTag = list.data?.nextPageTag ?: ""
        if(nextPageTag.isNullOrEmpty()){
            rvPosts.clearOnScrollListeners()
        }
        list.data?.posts?.let { redditPostListItem.data?.posts?.addAll(it) }
    }

    private fun handleError(failure: Throwable) {
        hideLoading()
        showFailed()
        Toast.makeText(this, failure.message, Toast.LENGTH_SHORT).show()
    }

    private fun showFailed() {
        isFailed = true
        postAdapter.showFailed()
    }

    private fun hideFailed() {
        isFailed = false
        postAdapter.hideFailed()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putParcelable(ARG_LIST, redditPostListItem)
        outState?.putInt(ARG_LIST_POSITION_X, rvPosts.scrollX)
    }
}
