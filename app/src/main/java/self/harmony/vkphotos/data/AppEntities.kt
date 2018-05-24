package self.harmony.vkphotos.data

data class PhotoBlock(
        var imageUrl: String,
        var largeImageUrl: String,
        var text: String,
        var reposts: Int,
        var likes: Int
)

data class PhotoBlocksData(var count: Int, var items: List<PhotoBlock>)