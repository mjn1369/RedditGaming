package apps.mjn.domain.entity

data class RedditPostList (
    val data: RedditPostListData
)

data class RedditPostListData (
    val dist: Int?,
    val children: List<RedditPostContainer>,
    val after: String?
)