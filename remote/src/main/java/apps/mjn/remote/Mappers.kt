package apps.mjn.remote

import apps.mjn.domain.entity.RedditPost
import apps.mjn.domain.entity.RedditPostContainer
import apps.mjn.domain.entity.RedditPostList
import apps.mjn.domain.entity.RedditPostListData
import apps.mjn.remote.dto.RedditPostContainerDTO
import apps.mjn.remote.dto.RedditPostDTO
import apps.mjn.remote.dto.RedditPostListDTO
import apps.mjn.remote.dto.RedditPostListDataDTO

fun RedditPostListDTO.toRedditPostList() =
    RedditPostList(data.toRedditPostListData())

fun RedditPostListDataDTO.toRedditPostListData() =
    RedditPostListData(dist, children.map { it.toRedditPostContainer() }, after)

fun RedditPostContainerDTO.toRedditPostContainer() =
    RedditPostContainer(data?.toRedditPost())

fun RedditPostDTO.toRedditPost() =
    RedditPost(title, score, subreddit, "${RemoteConstants.BASE_URL}${permalink?.substring(1)}")