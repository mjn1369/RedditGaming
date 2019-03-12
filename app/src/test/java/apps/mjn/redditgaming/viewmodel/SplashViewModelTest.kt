package apps.mjn.redditgaming.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import apps.mjn.domain.entity.RedditPostList
import apps.mjn.domain.failure.Failure
import apps.mjn.domain.interactor.GetRedditListUseCase
import apps.mjn.redditgaming.RedditPostListFactory
import apps.mjn.redditgaming.RedditPostListFactory.Factory.COUNT
import apps.mjn.redditgaming.extension.toRedditPostListItem
import apps.mjn.redditgaming.ui.model.ResourceState
import apps.mjn.redditgaming.ui.viewmodel.GamingListViewModel
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SplashViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getRedditListUseCase: GetRedditListUseCase = mockk(relaxed = true)
    private val viewModel: GamingListViewModel =
        GamingListViewModel(getRedditListUseCase)

    @Before
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `getRedditListUseCase executes`() {
        viewModel.load()
        verify(exactly = 1) { getRedditListUseCase.execute(any(), any(), any()) }
    }

    @Test
    fun `getRedditListUseCase success`() {
        val success = slot<(list: RedditPostList) -> Unit>()

        every {
            getRedditListUseCase.execute(any(), capture(success), any())
        } answers {
            success.invoke(RedditPostListFactory.makeRedditPostList(COUNT))
        }

        viewModel.load()
        assert(viewModel.getData().value?.resourceState == ResourceState.SUCCESS)
    }

    @Test
    fun `getRedditListUseCase success and contains data`() {
        val list: RedditPostList = RedditPostListFactory.makeRedditPostList(COUNT)
        val success = slot<(list: RedditPostList) -> Unit>()

        every {
            getRedditListUseCase.execute(any(), capture(success), any())
        } answers {
            success.invoke(list)
        }

        viewModel.load()
        assert(viewModel.getData().value?.resourceState == ResourceState.SUCCESS)
        assert(viewModel.getData().value?.data == list.toRedditPostListItem())
    }

    @Test
    fun `getRedditListUseCase success and contains no error`() {
        val list: RedditPostList = RedditPostListFactory.makeRedditPostList(COUNT)
        val success = slot<(list: RedditPostList) -> Unit>()

        every {
            getRedditListUseCase.execute(any(), capture(success), any())
        } answers {
            success.invoke(list)
        }

        viewModel.load()
        assert(viewModel.getData().value?.resourceState == ResourceState.SUCCESS)
        assert(viewModel.getData().value?.failure == null)
    }

    @Test
    fun `getRedditListUseCase failure`() {
        val error = slot<(throwable: Throwable) -> Unit>()

        every {
            getRedditListUseCase.execute(any(), any(), capture(error))
        } answers {
            error.invoke(Failure.ServerError("Server Error"))
        }

        viewModel.load()
        assert(viewModel.getData().value?.resourceState == ResourceState.ERROR)
    }

    @Test
    fun `getRedditListUseCase fails and contains error data`() {
        val error = slot<(throwable: Throwable) -> Unit>()

        every {
            getRedditListUseCase.execute(any(), any(), capture(error))
        } answers {
            error.invoke(Failure.ServerError("Server Error"))
        }

        viewModel.load()
        assert(viewModel.getData().value?.resourceState == ResourceState.ERROR)
        assert(viewModel.getData().value?.failure is Failure.ServerError)
    }

    @Test
    fun `getRedditListUseCase fails and contain no data`() {
        val error = slot<(throwable: Throwable) -> Unit>()

        every {
            getRedditListUseCase.execute(any(), any(), capture(error))
        } answers {
            error.invoke(Failure.ServerError("Server Error"))
        }

        viewModel.load()
        assert(viewModel.getData().value?.resourceState == ResourceState.ERROR)
        assert(viewModel.getData().value?.data == null)
    }

    @Test
    fun `getRedditListUseCase returns loading`() {
        viewModel.load()
        assert(viewModel.getData().value?.resourceState == ResourceState.LOADING)
    }

    @Test
    fun `getRedditListUseCase contains no data when loading`() {
        viewModel.load()
        assert(viewModel.getData().value?.resourceState == ResourceState.LOADING)
        assert(viewModel.getData().value?.data == null)
    }

    @Test
    fun `getRedditListUseCase contains no error when loading`() {
        viewModel.load()
        assert(viewModel.getData().value?.resourceState == ResourceState.LOADING)
        assert(viewModel.getData().value?.failure == null)
    }
}