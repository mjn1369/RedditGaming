package apps.mjn.domain.interactor

import apps.mjn.domain.entity.RedditPostList
import apps.mjn.domain.executer.PostExecutionThread
import apps.mjn.domain.executer.UseCaseExecutor
import apps.mjn.domain.interactor.base.SingleUseCase
import apps.mjn.domain.repository.RedditListRepository
import io.reactivex.Single
import javax.inject.Inject

class GetRedditListUseCase @Inject constructor(
    private val redditListRepository: RedditListRepository,
    useCaseExecutor: UseCaseExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<RedditPostList, GetRedditListUseCase.Params>(useCaseExecutor, postExecutionThread) {

    override fun buildSingle(params: Params): Single<RedditPostList> {
        return redditListRepository.getList(params.after)
    }

    data class Params(val after: String = "")

}