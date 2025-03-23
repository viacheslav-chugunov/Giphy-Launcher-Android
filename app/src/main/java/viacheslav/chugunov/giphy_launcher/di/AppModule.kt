package viacheslav.chugunov.giphy_launcher.di

import org.koin.dsl.module
import viacheslav.chugunov.core.util.AppSecrets
import viacheslav.chugunov.giphy_launcher.BuildConfig

val AppModule = module {
    single<AppSecrets> {
        AppSecrets.Default(
            giphyApiKey = BuildConfig.GIPHY_API_TOKEN
        )
    }
}