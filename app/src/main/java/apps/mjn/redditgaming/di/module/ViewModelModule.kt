package apps.mjn.redditgaming.di.module

import androidx.lifecycle.ViewModelProvider
import apps.mjn.redditgaming.core.ViewModelFactory
import dagger.Binds

import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}