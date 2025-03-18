package viacheslav.chugunov.network.datasource

import androidx.annotation.IntRange
import retrofit2.http.GET
import retrofit2.http.Query
import viacheslav.chugunov.network.model.GiphyPagingResponseDto

internal interface GiphyApi {
    @GET("gifs/trending")
    suspend fun trending(
        @Query("api_key") apiKey: String,
        @IntRange(from = 1) @Query("limit") limit: Int,
        @IntRange(from = 499) @Query("offset") offset: Int
    ): GiphyPagingResponseDto

    @GET("gifs/search")
    suspend fun search(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @IntRange(from = 1, to = 50) @Query("limit") limit: Int,
        @IntRange(from = 0, to = 4999) @Query("offset") offset: Int
    ): GiphyPagingResponseDto
}