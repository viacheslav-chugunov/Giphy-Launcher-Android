package viacheslav.chugunov.network.mapper

import viacheslav.chugunov.core.model.Gif
import viacheslav.chugunov.core.model.GifsSearchResult
import viacheslav.chugunov.core.model.Paging
import viacheslav.chugunov.network.model.GiphySearchResponseDto

internal interface GiphySearchResponseDtoToGifsSearchResultMapper {
    fun map(dto: GiphySearchResponseDto): GifsSearchResult

    class Default : GiphySearchResponseDtoToGifsSearchResultMapper {
        override fun map(dto: GiphySearchResponseDto): GifsSearchResult {
            return GifsSearchResult(
                gifs = dto.data.map {
                    Gif(
                        url = it.embedUrl
                    )
                },
                paging = Paging(
                    got = dto.pagination.offset + dto.pagination.offset,
                    total = dto.pagination.totalCount
                )
            )
        }
    }
}