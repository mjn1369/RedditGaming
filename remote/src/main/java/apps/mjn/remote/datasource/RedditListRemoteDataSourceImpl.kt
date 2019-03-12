package apps.mjn.remote.datasource

import apps.mjn.data.datasource.RedditListRemoteDataSource
import apps.mjn.domain.entity.RedditPostList
import apps.mjn.remote.RemoteConstants
import apps.mjn.remote.extension.toRxSingle
import apps.mjn.remote.retrofit.RedditPostListService
import apps.mjn.remote.toRedditPostList
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RedditListRemoteDataSourceImpl @Inject constructor(okHttpClient: OkHttpClient) : RedditListRemoteDataSource {

    private val service: RedditPostListService = Retrofit.Builder().baseUrl(RemoteConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient).build().create(RedditPostListService::class.java)

    override fun getList(after: String): Single<RedditPostList> {
        return service.getGamingList(after).toRxSingle().map { it.toRedditPostList() }
    }

}