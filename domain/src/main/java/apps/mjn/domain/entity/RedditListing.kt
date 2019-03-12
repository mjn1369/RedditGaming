package apps.mjn.domain.entity

data class RedditListing (
    val data: RedditListingItem
)

data class RedditListingItem (
    val dist: Int?,
    val children: List<RedditPostContainer>,
    val after: String?
)