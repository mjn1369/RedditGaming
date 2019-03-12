package apps.mjn.remote.mapper

import apps.mjn.domain.entity.RedditPostList
import apps.mjn.remote.dto.RedditPostListDTO
import apps.mjn.remote.factory.RedditPostListFactory
import apps.mjn.remote.factory.RedditPostListFactory.Factory.DIST
import apps.mjn.remote.toRedditPostContainer
import apps.mjn.remote.toRedditPostList
import apps.mjn.remote.toRedditPostListData
import org.junit.Assert
import org.junit.Test

class MappersTest {

    @Test
    fun `RedditPostListDto map to RedditPostList`() {
        val redditPostListDTO: RedditPostListDTO = RedditPostListFactory.makeRedditPostListDTO(DIST)
        val redditPostList: RedditPostList = redditPostListDTO.toRedditPostList()

        Assert.assertEquals(redditPostListDTO.data.toRedditPostListData(), redditPostList.data)
        Assert.assertEquals(redditPostListDTO.data.after, redditPostList.data.nextPageTag)
        Assert.assertEquals(redditPostListDTO.data.dist, redditPostList.data.childCount)
        Assert.assertEquals(redditPostListDTO.data.children.map { it.toRedditPostContainer() }, redditPostList.data.posts)
    }

}