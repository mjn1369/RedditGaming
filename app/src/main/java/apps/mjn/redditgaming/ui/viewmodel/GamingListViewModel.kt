package apps.mjn.redditgaming.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import apps.mjn.domain.entity.RedditPostList
import apps.mjn.domain.entity.RedditPostListItem
import apps.mjn.domain.interactor.GetRedditListUseCase
import apps.mjn.redditgaming.extension.toRedditPostListItem
import apps.mjn.redditgaming.ui.base.BaseViewModel
import apps.mjn.redditgaming.ui.model.Resource
import apps.mjn.redditgaming.ui.model.ResourceState
import javax.inject.Inject

class GamingListViewModel @Inject constructor(private val getRedditListUseCase: GetRedditListUseCase) :
    BaseViewModel() {

    private val data: MutableLiveData<Resource<RedditPostListItem>> = MutableLiveData()

    init {
        useCases += getRedditListUseCase
    }

    fun getData(): LiveData<Resource<RedditPostListItem>> = data

    fun load(nextPageTag: String = "") {
        data.value = Resource(ResourceState.LOADING)
        getRedditListUseCase.execute(GetRedditListUseCase.Params(nextPageTag), ::success, ::error)
    }

    private fun success(list: RedditPostList) {
        data.value = Resource(ResourceState.SUCCESS, list.toRedditPostListItem())
    }

    private fun error(throwable: Throwable) {
        data.value = Resource(ResourceState.ERROR, failure = throwable)
    }
}