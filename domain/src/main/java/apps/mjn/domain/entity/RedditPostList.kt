package apps.mjn.domain.entity

data class RedditPostList (
    val data: RedditPostListData
)

data class RedditPostListData (
    val childCount: Int?,
    val posts: List<RedditPostContainer>,
    val nextPageTag: String?
)