//package self.harmony.vkphotos.domain.mapper
//
//import self.harmony.vkphotos.data.*
//import java.lang.Exception
//
//
//    fun fromNetwork(source: Photo?): PhotoBlock {
//        source?.let {
//            return PhotoBlock(
//                    text = it.text ?: "",
//                    imageUrl = it.photo_604 ?: "",
//                    largeImageUrl = it.photo_604 ?: "",
//                    comments = it.post_id
//            )
//
//        } ?: throw Exception("Photo is null")
//    }
//
//    fun fromNetwork(source: List<Photo>?): ArrayList<PhotoBlock> {
//        source?.let {
//            val mappedSource = ArrayList<PhotoBlock>()
//            return source.mapTo(mappedSource) {
//                fromNetwork(it)
//            }
//        } ?: throw Exception("Photo list is null")
//    }
//
//    fun fromNetwork(source: PhotosResponse?): PhotoBlocksData {
//        source?.let {
//            val mappedItems = fromNetwork(source.items)
//            return PhotoBlocksData(
//                    count = source.count ?: 0,
//                    items = mappedItems
//            )
//
//        } ?: throw Exception("PhotosResponse is null")
//    }
//
//}