package viacheslav.chugunov.network.mock

import viacheslav.chugunov.network.datasource.GiphyApi
import viacheslav.chugunov.network.model.GiphyPagingResponseDto

internal class MockGiphyApi : GiphyApi {
    var tendingCalledCount: Int = 0
        private set
    var searchCalledCount: Int = 0
        private set
    var lastProcessedSearchQuery: String = ""
        private set
    var throwExceptionOnCallTrending: Throwable? = null
    var throwExceptionOnCallSearch: Throwable? = null

    override suspend fun trending(apiKey: String, limit: Int, offset: Int): GiphyPagingResponseDto {
        throwExceptionOnCallTrending?.let { throw it }
        tendingCalledCount++
        return GiphyPagingResponseDto()
    }

    override suspend fun search(
        apiKey: String,
        query: String,
        limit: Int,
        offset: Int
    ): GiphyPagingResponseDto {
        throwExceptionOnCallSearch?.let { throw it }
        lastProcessedSearchQuery = query
        searchCalledCount++
        return GiphyPagingResponseDto()
    }
}