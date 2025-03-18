package viacheslav.chugunov.network.mapper

import retrofit2.HttpException
import viacheslav.chugunov.core.util.NetworkException

internal interface ThrowableToNetworkExceptionMapper {
    fun map(error: Throwable): NetworkException

    class Default : ThrowableToNetworkExceptionMapper {
        override fun map(error: Throwable): NetworkException {
            if (error !is HttpException) return NetworkException(NetworkException.Cause.NoInternet)
            return when (error.code()) {
                404 -> NetworkException(NetworkException.Cause.NotFound)
                401,
                403,
                429 -> NetworkException(NetworkException.Cause.ServiceUnavailable)
                else -> NetworkException(NetworkException.Cause.NoInternet)
            }
        }
    }

}