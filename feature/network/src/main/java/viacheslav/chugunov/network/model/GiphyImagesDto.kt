package viacheslav.chugunov.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param fixedHeight Data on versions of this GIF with a fixed height of 200 pixels. Good for mobile use.
 * @param fixedHeightStill Data on a static image of this GIF with a fixed height of 200 pixels.
 * @param fixedHeightDownsampled Data on versions of this GIF with a fixed height of 200 pixels and the number of frames reduced to 6.
 * @param fixedWidth Data on versions of this GIF with a fixed width of 200 pixels. Good for mobile use.
 * @param fixedWidthStill Data on a static image of this GIF with a fixed width of 200 pixels.
 * @param fixedWidthDownsampled Data on versions of this GIF with a fixed width of 200 pixels and the number of frames reduced to 6.
 * @param fixedHeightSmall Data on versions of this GIF with a fixed height of 100 pixels. Good for mobile keyboards.
 * @param fixedHeightSmallStill Data on a static image of this GIF with a fixed height of 100 pixels.
 * @param fixedWidthSmall Data on versions of this GIF with a fixed width of 100 pixels. Good for mobile keyboards.
 * @param fixedWidthSmallStill Data on a static image of this GIF with a fixed width of 100 pixels.
 * @param downsized Data on a version of this GIF downsized to be under 2mb.
 * @param downsizedStill Data on a static preview image of the downsized version of this GIF.
 * @param downsizedLarge Data on a version of this GIF downsized to be under 8mb.
 * @param downsizedMedium Data on a version of this GIF downsized to be under 5mb.
 * @param downsizedSmall Data on a version of this GIF downsized to be under 200kb.
 * @param original Data on the original version of this GIF. Good for desktop use.
 * @param originalStill Data on a static preview image of the original GIF.
 * @param looping Data on the 15 second version of the GIF looping.
 * @param preview Data on a version of this GIF in .MP4 format limited to 50kb that displays the first 1-2 seconds of the GIF.
 * @param previewGif Data on a version of this GIF limited to 50kb that displays the first 1-2 seconds of the GIF.
 * @see <a href="https://developers.giphy.com/docs/api/schema#image-object">Documentation</a>
 * */
@Serializable
internal class GiphyImagesDto(
    @SerialName("fixed_height")
    val fixedHeight: GiphyImageDto = GiphyImageDto(),
    @SerialName("fixed_height_still")
    val fixedHeightStill: GiphyImageDto = GiphyImageDto(),
    @SerialName("fixed_height_downsampled")
    val fixedHeightDownsampled: GiphyImageDto = GiphyImageDto(),
    @SerialName("fixed_width")
    val fixedWidth: GiphyImageDto = GiphyImageDto(),
    @SerialName("fixed_width_still")
    val fixedWidthStill: GiphyImageDto = GiphyImageDto(),
    @SerialName("fixed_width_downsampled")
    val fixedWidthDownsampled: GiphyImageDto = GiphyImageDto(),
    @SerialName("fixed_height_small")
    val fixedHeightSmall: GiphyImageDto = GiphyImageDto(),
    @SerialName("fixed_height_small_still")
    val fixedHeightSmallStill: GiphyImageDto = GiphyImageDto(),
    @SerialName("fixed_width_small")
    val fixedWidthSmall: GiphyImageDto = GiphyImageDto(),
    @SerialName("fixed_width_small_still")
    val fixedWidthSmallStill: GiphyImageDto = GiphyImageDto(),
    @SerialName("downsized")
    val downsized: GiphyImageDto = GiphyImageDto(),
    @SerialName("downsized_still")
    val downsizedStill: GiphyImageDto = GiphyImageDto(),
    @SerialName("downsized_large")
    val downsizedLarge: GiphyImageDto = GiphyImageDto(),
    @SerialName("downsized_medium")
    val downsizedMedium: GiphyImageDto = GiphyImageDto(),
    @SerialName("downsized_small")
    val downsizedSmall: GiphyImageDto = GiphyImageDto(),
    @SerialName("original")
    val original: GiphyImageDto = GiphyImageDto(),
    @SerialName("original_still")
    val originalStill: GiphyImageDto = GiphyImageDto(),
    @SerialName("looping")
    val looping: GiphyImageDto = GiphyImageDto(),
    @SerialName("preview")
    val preview: GiphyImageDto = GiphyImageDto(),
    @SerialName("preview_gif")
    val previewGif: GiphyImageDto = GiphyImageDto(),
)