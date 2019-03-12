package apps.mjn.data.repository

import apps.mjn.data.datasource.RedditListRemoteDataSource
import apps.mjn.domain.entity.RedditPostList
import apps.mjn.domain.repository.RedditListRepository
import io.reactivex.Single
import javax.inject.Inject

class RedditListRepositoryImpl @Inject constructor(private val remoteDataSource: RedditListRemoteDataSource) :
    RedditListRepository {

    override fun getList(after: String): Single<RedditPostList> {
        return remoteDataSource.getList(after)
    }

}