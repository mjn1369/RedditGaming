package apps.mjn.redditgaming.ui.main

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import apps.mjn.domain.entity.RedditPostList
import apps.mjn.domain.entity.RedditPostListItem
import apps.mjn.redditgaming.ARG_LIST
import apps.mjn.redditgaming.R
import apps.mjn.redditgaming.extension.createViewModel
import apps.mjn.redditgaming.extension.observe
import apps.mjn.redditgaming.ui.base.BaseActivity
import apps.mjn.redditgaming.ui.model.Resource
import apps.mjn.redditgaming.ui.model.ResourceState
import apps.mjn.redditgaming.ui.viewmodel.GamingListViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: GamingListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = createViewModel(viewModelFactory) {
            observe(getData(), ::handleStates)
        }
        initList()
    }

    private fun initList(){
        var list = intent.getParcelableExtra(ARG_LIST) as RedditPostListItem
        Toast.makeText(this, list?.data?.posts?.get(0)?.data?.title, Toast.LENGTH_LONG).show()
    }

    private fun loadData(){
        viewModel.load(viewModel.getNextPageTag())
    }

    private fun handleStates(resource: Resource<RedditPostList>?) {
        resource?.let {
            when (resource.resourceState) {
                ResourceState.LOADING -> {}
                ResourceState.SUCCESS -> handleSuccess(resource.data!!)
                ResourceState.ERROR -> handleError(resource.failure!!)
            }
        }
    }

    private fun handleSuccess(list: RedditPostList) {
        Toast.makeText(this, list.data.posts[0].data?.title, Toast.LENGTH_LONG).show()
    }

    private fun handleError(failure: Throwable) {
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
