package viacheslav.chugunov.core.util

class NetworkException(
    val errorCause: Cause
) : Exception() {

    enum class Cause {
        /**
         * Element for the specified parameter was not found
         * */
        NotFound,
        /**
         * Indicates problems with the authorization token or any limit on the number of requests
         * for the specified authorization token
         * */
        ServiceUnavailable,
        /**
         * Trying to make a request without an internet connection. Used as a default error for
         * cases not specified above.
         * */
        NoInternet
    }

}