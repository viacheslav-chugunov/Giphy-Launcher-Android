package viacheslav.chugunov.network.mapper

import viacheslav.chugunov.core.model.Gif
import viacheslav.chugunov.core.model.PagingGifsResult
import viacheslav.chugunov.core.model.Paging
import viacheslav.chugunov.network.model.GiphyPagingResponseDto

internal interface GiphyPagingResponseDtoToPagingGifsResult {
    fun map(dto: GiphyPagingResponseDto): PagingGifsResult

    class Default : GiphyPagingResponseDtoToPagingGifsResult {
        override fun map(dto: GiphyPagingResponseDto): PagingGifsResult {
            return PagingGifsResult(
                gifs = dto.data.map {
                    Gif(
                        id = it.id,
                        previewUrl = it.images.previewGif.url,
                        fullUrl = it.images.fixedHeight.url,
                        username = it.user.displayName,
                        title = it.title,
                        createdAt = it.createDatetime
                    )
                },
                paging = Paging(
                    got = dto.pagination.offset + dto.pagination.count,
                    total = dto.pagination.totalCount
                )
            )
        }
    }
}