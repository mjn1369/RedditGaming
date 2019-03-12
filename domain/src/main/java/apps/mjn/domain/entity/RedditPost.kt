package apps.mjn.domain.entity

data class RedditPostContainer (
    val data: RedditPost?
)

data class RedditPost (
    val title: String?,
    val score: Long?,
    val subReddit: String?,
    val permalink: String?
)