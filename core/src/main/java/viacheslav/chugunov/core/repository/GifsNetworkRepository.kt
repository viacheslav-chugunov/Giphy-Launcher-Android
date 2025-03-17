package viacheslav.chugunov.core.repository

import viacheslav.chugunov.core.model.GifsSearchResult

interface GifsNetworkRepository {
    suspend fun search(query: String, limit: Int, offset: Int): GifsSearchResult
}