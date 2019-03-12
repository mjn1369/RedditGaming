package apps.mjn.remote.dto

import com.google.gson.annotations.SerializedName

data class RedditPostContainerDTO (
    @SerializedName("data")
    val data: RedditPostDTO?
)

data class RedditPostDTO (
    @SerializedName("title")
    val title: String?,
    @SerializedName("score")
    val score: Long?,
    @SerializedName("subreddit")
    val subreddit: String?,
    @SerializedName("permalink")
    val permalink: String?
)