package apps.mjn.remote.dto

import com.google.gson.annotations.SerializedName

data class RedditPostListDTO (
    @SerializedName("data")
    val data: RedditPostListDataDTO?
)

data class RedditPostListDataDTO (
    @SerializedName("dist")
    val dist: Int?,
    @SerializedName("children")
    val children: List<RedditPostContainerDTO>,
    @SerializedName("after")
    val after: String?
)