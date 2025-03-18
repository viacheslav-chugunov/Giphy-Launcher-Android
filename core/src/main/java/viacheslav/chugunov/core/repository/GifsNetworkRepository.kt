package viacheslav.chugunov.core.repository

import kotlinx.coroutines.flow.Flow
import viacheslav.chugunov.core.model.PagingGifsResult
import viacheslav.chugunov.core.util.AsyncResource

interface GifsNetworkRepository {
    suspend fun trending(limit: Int, offset: Int): AsyncResource<PagingGifsResult>
    suspend fun search(query: String, limit: Int, offset: Int): AsyncResource<PagingGifsResult>
}