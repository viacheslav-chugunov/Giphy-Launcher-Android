package viacheslav.chugunov.network.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import viacheslav.chugunov.core.repository.GifsNetworkRepository
import viacheslav.chugunov.network.datasource.GiphyApi
import viacheslav.chugunov.network.mapper.GiphySearchResponseDtoToGifsSearchResultMapper
import viacheslav.chugunov.network.repository.DefaultGifsNetworkRepository
import java.util.concurrent.TimeUnit

val NetworkModule = module {
    factory<Json> {
        Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }

    factory<ChuckerInterceptor> {
        ChuckerInterceptor.Builder(androidContext()).build()
    }

    factory<GiphySearchResponseDtoToGifsSearchResultMapper> {
        GiphySearchResponseDtoToGifsSearchResultMapper.Default()
    }

    factory<GiphyApi> {
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(get<ChuckerInterceptor>())
            .build()
        Retrofit.Builder()
            .baseUrl("https://api.giphy.com/v1/")
            .client(okHttpClient)
            .addConverterFactory(get<Json>().asConverterFactory(MediaType.get("application/json")))
            .build()
            .create(GiphyApi::class.java)
    }

    factory<GifsNetworkRepository> {
        DefaultGifsNetworkRepository(get(), get(), get(), get())
    }
}