package apps.mjn.data.repository

import apps.mjn.data.datasource.RedditListRemoteDataSource
import apps.mjn.data.factory.RedditPostListFactory
import apps.mjn.data.factory.RedditPostListFactory.Factory.AFTER
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import apps.mjn.data.factory.RedditPostListFactory.Factory.COUNT
import io.mockk.every
import io.reactivex.Single

class RedditListRepositoryImplTest {

    private val dataSource: RedditListRemoteDataSource = mockk()
    private val repository: RedditListRepositoryImpl = RedditListRepositoryImpl(dataSource)

    @Before
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `getList returns data`() {
        val list = RedditPostListFactory.makeRedditPostList(COUNT)
        every { dataSource.getList(AFTER) } returns Single.just(list)
        val successObserver = repository.getList(AFTER).test()
        with(successObserver) {
            assertComplete()
            assertNoErrors()
            assertValue(list)
        }
    }
}