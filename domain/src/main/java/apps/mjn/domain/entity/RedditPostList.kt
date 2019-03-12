package apps.mjn.domain.entity

data class RedditPostList (
    val data: RedditPostListItem
)

data class RedditPostListItem (
    val dist: Int?,
    val children: List<RedditPostContainer>,
    val after: String?
)