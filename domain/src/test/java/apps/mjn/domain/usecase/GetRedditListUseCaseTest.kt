package apps.mjn.domain.usecase

import apps.mjn.domain.entity.RedditPostList
import apps.mjn.domain.executer.PostExecutionThread
import apps.mjn.domain.executer.UseCaseExecutor
import apps.mjn.domain.factory.RedditPostListFactory.Factory.AFTER
import apps.mjn.domain.factory.RedditPostListFactory.Factory.DIST
import apps.mjn.domain.factory.RedditPostListFactory.Factory.makeRedditPostList
import apps.mjn.domain.interactor.GetRedditListUseCase
import apps.mjn.domain.repository.RedditListRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class GetRedditListUseCaseTest {

    private val repository: RedditListRepository = mockk()
    private val useCaseExecutor: UseCaseExecutor = mockk(relaxed = true)
    private val postExecutionThread: PostExecutionThread = mockk(relaxed = true)
    private val usecase: GetRedditListUseCase =
        GetRedditListUseCase(repository, useCaseExecutor, postExecutionThread)
    private val params = GetRedditListUseCase.Params(AFTER)

    @Before
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `getList method of repository was called`() {
        mockGetListResponse()
        usecase.execute(params, {}, {})
        verify(exactly = 1) { repository.getList(any()) }
    }

    @Test
    fun `correct arguments were passed to the getList method of repository`() {
        mockGetListResponse()
        usecase.execute(params, {}, {})
        verify { repository.getList(AFTER) }
    }

    @Test
    fun `repository completes`() {
        mockGetListResponse()
        usecase.buildSingle(params).test().assertComplete()
    }

    @Test
    fun `repository returns data`() {
        val list = makeRedditPostList(DIST)
        mockGetListResponse(list)
        usecase.buildSingle(params).test().assertValue(list)
    }

    private fun mockGetListResponse(list: RedditPostList = makeRedditPostList(DIST)) {
        every { repository.getList(any()) } returns Single.just(list)
    }
}