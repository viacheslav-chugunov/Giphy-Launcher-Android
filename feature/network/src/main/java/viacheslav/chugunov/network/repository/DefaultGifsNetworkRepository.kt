package viacheslav.chugunov.network.repository

import kotlinx.coroutines.withContext
import viacheslav.chugunov.core.model.PagingGifsResult
import viacheslav.chugunov.core.repository.GifsNetworkRepository
import viacheslav.chugunov.core.util.AppSecrets
import viacheslav.chugunov.core.util.AsyncResource
import viacheslav.chugunov.core.util.CoroutineDispatchers
import viacheslav.chugunov.network.datasource.GiphyApi
import viacheslav.chugunov.network.mapper.ThrowableToNetworkExceptionMapper
import viacheslav.chugunov.network.mapper.GiphyPagingResponseDtoToPagingGifsResult

internal class DefaultGifsNetworkRepository(
    private val giphyApi: GiphyApi,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val giphyPagingResponseDtoToPagingGifsResult: GiphyPagingResponseDtoToPagingGifsResult,
    private val throwableToNetworkExceptionMapper: ThrowableToNetworkExceptionMapper,
    private val appSecrets: AppSecrets
) : GifsNetworkRepository {

    override suspend fun trending(
        limit: Int,
        offset: Int
    ): AsyncResource<PagingGifsResult> = withContext(coroutineDispatchers.io) {
        try {
            val response = giphyApi.trending(appSecrets.giphyApiKey, limit, offset)
            AsyncResource.Success(giphyPagingResponseDtoToPagingGifsResult.map(response))
        } catch (e: Throwable) {
            AsyncResource.Failure(throwableToNetworkExceptionMapper.map(e))
        }
    }

    override suspend fun search(
        query: String,
        limit: Int,
        offset: Int
    ): AsyncResource<PagingGifsResult> = withContext(coroutineDispatchers.io) {
        try {
            val response = giphyApi.search(appSecrets.giphyApiKey, query.trim(), limit, offset)
            AsyncResource.Success(giphyPagingResponseDtoToPagingGifsResult.map(response))
        } catch (e: Throwable) {
            AsyncResource.Failure(throwableToNetworkExceptionMapper.map(e))
        }
    }

}