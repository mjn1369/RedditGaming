package apps.mjn.redditgaming.extension

import apps.mjn.domain.entity.*
import apps.mjn.redditgaming.ui.model.entity.RedditPostContainerItem
import apps.mjn.redditgaming.ui.model.entity.RedditPostItem
import apps.mjn.redditgaming.ui.model.entity.RedditPostListDataItem
import apps.mjn.redditgaming.ui.model.entity.RedditPostListItem

fun RedditPostList.toRedditPostListItem() =
        RedditPostListItem(
            data?.toRedditPostListDataItem()
        )

fun RedditPostListData.toRedditPostListDataItem() =
        RedditPostListDataItem(
            childCount,
            posts.map { it.toRedditPostContainerItem() } as ArrayList,
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