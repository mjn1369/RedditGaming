package apps.mjn.redditgaming.di.module

import android.content.Context
import apps.mjn.domain.executer.PostExecutionThread
import apps.mjn.domain.executer.UseCaseExecutor
import apps.mjn.redditgaming.core.RedditGamingApplication
import apps.mjn.redditgaming.executer.ExecutorThread
import apps.mjn.redditgaming.executer.UIThread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: RedditGamingApplication): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideUseCaseExecutor(): UseCaseExecutor = ExecutorThread()

    @Provides
    @Singleton
    fun providePostExecutionThread(): PostExecutionThread = UIThread()
}