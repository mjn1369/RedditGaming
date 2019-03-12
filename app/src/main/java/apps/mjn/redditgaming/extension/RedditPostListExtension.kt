package apps.mjn.redditgaming.extension

import apps.mjn.domain.entity.*

fun RedditPostList.toRedditPostListItem() =
        RedditPostListItem(
            data?.toRedditPostListDataItem()
        )

fun RedditPostListData.toRedditPostListDataItem() =
        RedditPostListDataItem(
            childCount,
            posts.map { it.toRedditPostContainerItem() },
            nextPageTag
        )

fun RedditPostContainer.toRedditPostContainerItem() =
        RedditPostContainerItem(
            data?.toRedditPostItem()
        )

fun RedditPost.toRedditPostItem() =
        RedditPostItem(
            title,
            score,
            subReddit,
            permanentLink
        )