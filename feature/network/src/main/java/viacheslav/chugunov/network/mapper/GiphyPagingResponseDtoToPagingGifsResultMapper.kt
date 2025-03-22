package viacheslav.chugunov.network.mapper

import viacheslav.chugunov.core.model.Gif
import viacheslav.chugunov.core.model.PagingGifsResult
import viacheslav.chugunov.core.model.Paging
import viacheslav.chugunov.network.model.GiphyPagingResponseDto
import java.text.SimpleDateFormat
import java.util.Locale

internal interface GiphyPagingResponseDtoToPagingGifsResultMapper {
    fun map(dto: GiphyPagingResponseDto): PagingGifsResult

    class Default : GiphyPagingResponseDtoToPagingGifsResultMapper {
        override fun map(dto: GiphyPagingResponseDto): PagingGifsResult {
            val timeParser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val timeFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
            return PagingGifsResult(
                gifs = dto.data.map {
                    Gif(
                        id = it.id,
                        previewUrl = it.images.previewGif.url,
                        fullUrl = it.images.fixedHeight.url,
                        username = it.user.displayName,
                        title = it.title,
                        createdAt = runCatching {
                            timeFormatter.format(timeParser.parse(it.importDatetime)!!)
                        }.getOrDefault("")
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