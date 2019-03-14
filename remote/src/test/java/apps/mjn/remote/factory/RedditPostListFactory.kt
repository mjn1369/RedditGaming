package apps.mjn.remote.factory

import apps.mjn.remote.factory.DataFactory.Factory.randomLong
import apps.mjn.remote.factory.DataFactory.Factory.randomString
import apps.mjn.domain.entity.RedditPost
import apps.mjn.domain.entity.RedditPostContainer
import apps.mjn.domain.entity.RedditPostList
import apps.mjn.domain.entity.RedditPostListData
import apps.mjn.remote.dto.RedditPostContainerDTO
import apps.mjn.remote.dto.RedditPostDTO
import apps.mjn.remote.dto.RedditPostListDTO
import apps.mjn.remote.dto.RedditPostListDataDTO

internal class RedditPostListFactory {

    companion object Factory {
        private const val AFTER = ""
        const val COUNT = 25

        fun makeRedditPostList(postCount: Int): RedditPostList {
            val postContainers = ArrayList<RedditPostContainer>()
            repeat(postCount) {
                postContainers.add(makePostContainer())
            }
            return RedditPostList(makePostListData(COUNT, postContainers, AFTER))
        }

        fun makeRedditPostListDTO(postCount: Int): RedditPostListDTO {
            val postContainers = ArrayList<RedditPostContainerDTO>()
            repeat(postCount) {
                postContainers.add(makePostContainerDTO())
            }
            return RedditPostListDTO(makePostListDataDTO(COUNT, postContainers, AFTER))
        }

        private fun makePostListData(dist: Int, children: ArrayList<RedditPostContainer>, after: String) = RedditPostListData(
            dist, children, after
        )

        private fun makePostListDataDTO(dist: Int, children: ArrayList<RedditPostContainerDTO>, after: String) = RedditPostListDataDTO(
            dist, children, after
        )

        private fun makePostContainer() = RedditPostContainer(
            RedditPost(randomString(20), randomLong(), randomString(10), randomString(30))
        )

        private fun makePostContainerDTO() = RedditPostContainerDTO(
            RedditPostDTO(randomString(20), randomLong(), randomString(10), randomString(30))
        )
    }

}