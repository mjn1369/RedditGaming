package apps.mjn.redditgaming.ui.main

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import apps.mjn.domain.entity.RedditPostItem
import apps.mjn.domain.entity.RedditPostList
import apps.mjn.domain.entity.RedditPostListItem
import apps.mjn.redditgaming.ARG_LIST
import apps.mjn.redditgaming.R
import apps.mjn.redditgaming.extension.createViewModel
import apps.mjn.redditgaming.extension.observe
import apps.mjn.redditgaming.extension.toRedditPostListItem
import apps.mjn.redditgaming.ui.base.BaseActivity
import apps.mjn.redditgaming.ui.main.adapter.PostAdapter
import apps.mjn.redditgaming.ui.main.adapter.VerticalSpaceItemDecoration
import apps.mjn.redditgaming.ui.model.Resource
import apps.mjn.redditgaming.ui.model.ResourceState
import apps.mjn.redditgaming.ui.viewmodel.GamingListViewModel
import apps.mjn.redditgaming.util.recyclerview.InfiniteLinearScrollListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: GamingListViewModel
    private var nextPageTag: String = ""
    var postAdapter = PostAdapter(ArrayList()) {
        onPostClick(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = createViewModel(viewModelFactory) {
            observe(getData(), ::handleStates)
        }
        initList()
    }

    private fun initList() {
        rvPosts.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.space_small)))
        val list = intent.getParcelableExtra(ARG_LIST) as RedditPostListItem?
        rvPosts.adapter = postAdapter
        list?.let { postItem ->
            nextPageTag = postItem.data?.nextPageTag ?: ""
            addToList(postItem.data?.posts?.mapNotNull { it.data })
        } ?: loadData()
        rvPosts.addOnScrollListener(InfiniteLinearScrollListener(5, rvPosts.layoutManager as LinearLayoutManager) {
            loadMore()
        })
    }

    private fun addToList(items: List<RedditPostItem>?) {
        postAdapter.addItems(items)
    }

    private fun loadData() {
        viewModel.load(nextPageTag)
    }

    private fun loadMore() {
        showLoading()
        loadData()
    }

    private fun onPostClick(redditPostItem: RedditPostItem) {
        Toast.makeText(this, "Clicked on ${redditPostItem.title}", Toast.LENGTH_LONG).show()
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
        loadingPosts.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingPosts.visibility = View.GONE
    }

    private fun handleSuccess(list: RedditPostListItem) {
        hideLoading()
        addToList(list.data?.posts?.mapNotNull { it.data })
        nextPageTag = list.data?.nextPageTag ?: ""
    }

    private fun handleError(failure: Throwable) {
        hideLoading()
        showSnackBar(failure.message) { loadData() }
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
