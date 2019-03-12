package apps.mjn.redditgaming.ui.splash

import androidx.lifecycle.ViewModel
import apps.mjn.data.datasource.RedditListRemoteDataSource
import apps.mjn.data.repository.RedditListRepositoryImpl
import apps.mjn.domain.repository.RedditListRepository
import apps.mjn.redditgaming.di.annotation.ViewModelKey
import apps.mjn.remote.datasource.RedditListRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SplashModule {

    @ContributesAndroidInjector
    internal abstract fun splashActivity(): SplashActivity

    @Binds
    @IntoMap
    @ViewModelKey(GamingListViewModel::class)
    abstract fun bindGamingListViewModel(gamingListViewModel: GamingListViewModel): ViewModel

    @Binds
    abstract fun bindRedditListRepository(redditListRepository: RedditListRepositoryImpl): RedditListRepository

    @Binds
    abstract fun bindRedditListRemoteDataSource(redditListRemoteDataSource: RedditListRemoteDataSourceImpl): RedditListRemoteDataSource
}