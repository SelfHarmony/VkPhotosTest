package self.harmony.vkphotos.data

data class UserPhotoResponse(var response: Response)

data class Response(val more: Int = 0,
                    val count: Int = 0,
                    val items: List<UserPhoto>?)


data class SizesItem(val width: Int = 0,
                     val type: String = "",
                     val url: String = "",
                     val height: Int = 0)


data class Reposts(val count: Int = 0)


data class UserPhoto(val date: Int = 0,
                     val realOffset: Int = 0,
                     val sizes: List<SizesItem>?,
                     val ownerId: Int = 0,
                     val albumId: Int = 0,
                     val id: Int = 0,
                     val text: String = "",
                     val reposts: Reposts,
                     val likes: Likes)


data class Likes(val userLikes: Int = 0,
                 val count: Int = 0)



