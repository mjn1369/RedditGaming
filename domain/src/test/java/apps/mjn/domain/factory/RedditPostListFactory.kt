package apps.mjn.domain.factory

import apps.mjn.domain.entity.RedditPost
import apps.mjn.domain.entity.RedditPostContainer
import apps.mjn.domain.entity.RedditPostList
import apps.mjn.domain.entity.RedditPostListData
import apps.mjn.domain.factory.DataFactory.Factory.randomString
import apps.mjn.domain.factory.DataFactory.Factory.randomLong

internal class RedditPostListFactory {

    companion object Factory {
        const val AFTER = ""
        const val COUNT = 25

        fun makeRedditPostList(postCount: Int): RedditPostList {
            val postContainers = mutableListOf<RedditPostContainer>()
            repeat(postCount) {
                postContainers.add(makePostContainer())
            }
            return RedditPostList(makePostListData(COUNT, postContainers, AFTER))
        }

        private fun makePostListData(dist: Int, children: List<RedditPostContainer>, after: String) = RedditPostListData(
            dist, children, after
        )

        private fun makePostContainer() = RedditPostContainer(
            RedditPost(randomString(20), randomLong(), randomString(10), randomString(30))
        )
    }

}