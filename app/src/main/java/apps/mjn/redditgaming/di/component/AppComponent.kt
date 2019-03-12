package apps.mjn.redditgaming.di.component

import apps.mjn.redditgaming.core.RedditGamingApplication
import apps.mjn.redditgaming.di.module.AppModule
import apps.mjn.redditgaming.di.module.NetworkModule
import apps.mjn.redditgaming.di.module.ViewModelModule
import apps.mjn.redditgaming.ui.splash.SplashModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        AppModule::class,
        NetworkModule::class,
        SplashModule::class
    ]
)
interface AppComponent : AndroidInjector<RedditGamingApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<RedditGamingApplication>()

}