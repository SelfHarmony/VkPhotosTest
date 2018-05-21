package self.harmony.vkphotos.api.models

data class PhotosResponseHolder(var response: PhotosResponse?)

data class PhotosResponse(var count: Int?, var items: List<Photo>)

data class Photo(var id: Int?, var post_id: Int, var photo_604: String?, var text: String)

