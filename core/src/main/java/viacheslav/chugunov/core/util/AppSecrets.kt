package viacheslav.chugunov.core.util

interface AppSecrets {
    val giphyApiKey: String

    class Default(
        override val giphyApiKey: String
    ) : AppSecrets
}