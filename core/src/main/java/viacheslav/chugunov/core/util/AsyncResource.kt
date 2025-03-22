package viacheslav.chugunov.core.util


sealed interface AsyncResource<T> {
    class Loading<T> : AsyncResource<T>
    class Success<T>(val data: T) : AsyncResource<T>
    class Failure<T>(val error: Throwable) : AsyncResource<T> {
        val networkException: NetworkException
            get() = error as? NetworkException ?: NetworkException(NetworkException.Cause.NoInternet)
    }

    val dataOrNull: T?
        get() = if (this is Success<T>) data else null
}