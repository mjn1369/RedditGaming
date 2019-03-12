package apps.mjn.remote.retrofit

import apps.mjn.remote.dto.RedditPostListDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditPostListService {
    @GET("r/gaming/top.json")
    fun getGamingList(@Query("after") after: String): Call<RedditPostListDTO>
}