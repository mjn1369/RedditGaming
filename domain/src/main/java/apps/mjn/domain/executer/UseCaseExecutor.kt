package apps.mjn.domain.executer

import io.reactivex.Scheduler

interface UseCaseExecutor {
    val scheduler: Scheduler
}