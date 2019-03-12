package apps.mjn.redditgaming.ui.splash

import apps.mjn.domain.interactor.GetRedditListUseCase
import apps.mjn.redditgaming.ui.base.BaseViewModel
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val redditListUseCase: GetRedditListUseCase) :
    BaseViewModel() {

    init {
        useCases += redditListUseCase
    }
}