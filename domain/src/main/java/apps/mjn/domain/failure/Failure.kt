package apps.mjn.domain.failure

sealed class Failure(override val message: String) : Throwable(message) {
    class NetworkConnectionError(message: String) : Failure(message)
    class ServerError(message: String) : Failure(message)
    class NotFoundError(message: String) : Failure(message)
}