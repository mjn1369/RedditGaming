package apps.mjn.redditgaming.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import apps.mjn.redditgaming.ARG_LIST
import apps.mjn.redditgaming.R
import apps.mjn.redditgaming.extension.createViewModel
import apps.mjn.redditgaming.extension.observe
import apps.mjn.redditgaming.ui.base.BaseActivity
import apps.mjn.redditgaming.ui.main.MainActivity
import apps.mjn.redditgaming.ui.model.Resource
import apps.mjn.redditgaming.ui.model.ResourceState
import apps.mjn.redditgaming.ui.model.entity.RedditPostListItem
import apps.mjn.redditgaming.ui.viewmodel.GamingListViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    private lateinit var viewModel: GamingListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = createViewModel(viewModelFactory) {
            observe(getData(), ::handleStates)
        }
        loadData()
    }

    private fun loadData() {
        viewModel.load()
    }

    private fun handleStates(resource: Resource<RedditPostListItem>?) {
        resource?.let {
            when (resource.resourceState) {
                ResourceState.LOADING -> {
                }
                ResourceState.SUCCESS -> handleSuccess(resource.data!!)
                ResourceState.ERROR -> handleError(resource.failure!!)
            }
        }
    }

    private fun handleSuccess(list: RedditPostListItem) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(ARG_LIST, list)
        startActivity(intent)
        finish()
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
