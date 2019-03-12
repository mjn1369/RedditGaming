package apps.mjn.redditgaming.ui.splash

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SplashModule {
    @ContributesAndroidInjector
    internal abstract fun splashActivity(): SplashActivity
}