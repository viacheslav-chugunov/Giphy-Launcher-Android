package viacheslav.chugunov.network.repository

import kotlinx.coroutines.withContext
import viacheslav.chugunov.core.model.PagingGifsResult
import viacheslav.chugunov.core.repository.GifsNetworkRepository
import viacheslav.chugunov.core.util.AppSecrets
import viacheslav.chugunov.core.util.AsyncResource
import viacheslav.chugunov.core.util.CoroutineDispatchers
import viacheslav.chugunov.network.datasource.GiphyApi
import viacheslav.chugunov.network.mapper.ThrowableToNetworkExceptionMapper
import viacheslav.chugunov.network.mapper.GiphyPagingResponseDtoToPagingGifsResultMapper

internal class DefaultGifsNetworkRepository(
    private val giphyApi: GiphyApi,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val giphyPagingResponseDtoToPagingGifsResultMapper: GiphyPagingResponseDtoToPagingGifsResultMapper,
    private val throwableToNetworkExceptionMapper: ThrowableToNetworkExceptionMapper,
    private val appSecrets: AppSecrets
) : GifsNetworkRepository {

    override suspend fun trending(
        limit: Int,
        offset: Int
    ): AsyncResource<PagingGifsResult> = withContext(coroutineDispatchers.io) {
        if (limit <= 0) throw IllegalArgumentException("limit must be greater than zero, but limit = $limit was passed")
        if (offset !in 0..499) throw IllegalArgumentException("offset must be in range from 0 to 499, but offset = $offset was passed")
        try {
            val response = giphyApi.trending(appSecrets.giphyApiKey, limit, offset)
            AsyncResource.Success(giphyPagingResponseDtoToPagingGifsResultMapper.map(response))
        } catch (e: Throwable) {
            AsyncResource.Failure(throwableToNetworkExceptionMapper.map(e))
        }
    }

    override suspend fun search(
        query: String,
        limit: Int,
        offset: Int
    ): AsyncResource<PagingGifsResult> = withContext(coroutineDispatchers.io) {
        if (query.isBlank()) throw IllegalArgumentException("query must not be blank, but query = $query was passed")
        if (limit <= 0) throw IllegalArgumentException("limit must be greater than zero, but limit = $limit was passed")
        if (offset !in 0..4999) throw IllegalArgumentException("offset must be in range from 0 to 4999, but offset = $offset was passed")
        try {
            val response = giphyApi.search(appSecrets.giphyApiKey, query.trim(), limit, offset)
            AsyncResource.Success(giphyPagingResponseDtoToPagingGifsResultMapper.map(response))
        } catch (e: Throwable) {
            AsyncResource.Failure(throwableToNetworkExceptionMapper.map(e))
        }
    }

}