package viacheslav.chugunov.core.util


sealed interface AsyncResource<T> {
    class Loading<T> : AsyncResource<T>
    class Success<T>(val data: T) : AsyncResource<T>
    class Failure<T>(val error: Throwable) : AsyncResource<T> {
        val networkException: NetworkException
            get() = error as? NetworkException ?: NetworkException(NetworkException.Cause.NoInternet)
    }

    val isSuccess: Boolean
        get() = this is Success

    val isLoading: Boolean
        get() = this is Loading

    val isFailure: Boolean
        get() = this is Failure

    fun <R> map(mapper: (data: T) -> R): AsyncResource<R> = when (this) {
        is Failure -> Failure(error)
        is Loading -> Loading()
        is Success -> Success(mapper(data))
    }

    val dataOrNull: T?
        get() = if (this is Success<T>) data else null
}