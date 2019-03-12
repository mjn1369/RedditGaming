package apps.mjn.data.datasource

import apps.mjn.domain.entity.RedditPostList
import io.reactivex.Single

interface RedditListRemoteDataSource {

    fun getList(after: String): Single<RedditPostList>

}