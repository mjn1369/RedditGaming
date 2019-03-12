package apps.mjn.redditgaming.ui.main

import androidx.lifecycle.ViewModel
import apps.mjn.redditgaming.ui.viewmodel.GamingListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @Binds
    abstract fun bindGamingListViewModel(gamingListViewModel: GamingListViewModel): ViewModel
}