package self.harmony.vkphotos.domain.mapper

import self.harmony.vkphotos.data.PhotoBlock
import self.harmony.vkphotos.data.PhotoBlocksData
import self.harmony.vkphotos.data.UserPhoto
import self.harmony.vkphotos.data.UserPhotoResponse
import java.lang.Exception

object UserPhotoMapper {

    fun fromNetwork(source: UserPhoto): PhotoBlock {
        source.let {
            return PhotoBlock(
                    text = it.text,
                    imageUrl = findSmallPhotoSize(it),
                    largeImageUrl = findLargePhotoSize(it),
                    reposts = it.reposts.count,
                    likes = it.likes.count
            )
        }
    }

    fun fromNetwork(source: List<UserPhoto>?): ArrayList<PhotoBlock> {
        source?.let {
            val mappedSource = ArrayList<PhotoBlock>()
            return source.mapTo(mappedSource) {
                fromNetwork(it)
            }
        } ?: throw Exception("Photo list is null")
    }

    fun fromNetwork(source: UserPhotoResponse?): PhotoBlocksData {
        source?.let {
            val mappedItems = fromNetwork(source.response.items)
            return PhotoBlocksData(
                    count = source.response.count,
                    items = mappedItems
            )

        } ?: throw Exception("PhotosResponse is null")
    }

    private fun findLargePhotoSize(source: UserPhoto) =
            source.sizes?.find { it.type == "z" }?.url ?: source.sizes?.find { it.type == "y" }?.url ?: source.sizes?.find { it.type == "x" }?.url ?:""

    private fun findSmallPhotoSize(source: UserPhoto) =
            source.sizes?.find { it.type == "r" }?.url ?: source.sizes?.find { it.type == "m" }?.url ?:""

}