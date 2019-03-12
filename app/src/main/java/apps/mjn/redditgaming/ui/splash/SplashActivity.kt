package apps.mjn.redditgaming.ui.splash

import android.os.Bundle
import android.widget.Toast
import apps.mjn.domain.entity.RedditPostList
import apps.mjn.domain.failure.Failure
import apps.mjn.redditgaming.R
import apps.mjn.redditgaming.extension.createViewModel
import apps.mjn.redditgaming.extension.observe
import apps.mjn.redditgaming.ui.base.BaseActivity
import apps.mjn.redditgaming.ui.model.Resource
import apps.mjn.redditgaming.ui.model.ResourceState

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

    private fun loadData(){
        viewModel.load()
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
        Toast.makeText(this, failure.message, Toast.LENGTH_LONG).show()
    }
}
