package viacheslav.chugunov.network.repository

import kotlinx.coroutines.withContext
import retrofit2.HttpException
import viacheslav.chugunov.core.model.GifsSearchResult
import viacheslav.chugunov.core.repository.GifsNetworkRepository
import viacheslav.chugunov.core.util.AppSecrets
import viacheslav.chugunov.core.util.CoroutineDispatchers
import viacheslav.chugunov.network.datasource.GiphyApi
import viacheslav.chugunov.network.mapper.GiphySearchResponseDtoToGifsSearchResultMapper

internal class DefaultGifsNetworkRepository(
    private val giphyApi: GiphyApi,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val giphySearchResponseDTOToGifsSearchResultMapper: GiphySearchResponseDtoToGifsSearchResultMapper,
    private val appSecrets: AppSecrets
) : GifsNetworkRepository {

    override suspend fun search(
        query: String,
        limit: Int,
        offset: Int
    ): GifsSearchResult = withContext(coroutineDispatchers.io) {
        val response = giphyApi.search(appSecrets.giphyApiKey, query, limit, offset)
        if (!response.isSuccessful) throw HttpException(response)
        giphySearchResponseDTOToGifsSearchResultMapper.map(response.body()!!)
    }

}