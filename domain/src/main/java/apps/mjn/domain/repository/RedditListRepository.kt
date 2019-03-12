package apps.mjn.domain.repository

import apps.mjn.domain.entity.RedditPostList
import io.reactivex.Single

interface RedditListRepository {
    fun getList(after: String): Single<RedditPostList>
}