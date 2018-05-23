package self.harmony.vkphotos.data.repository

import self.harmony.vkphotos.data.PhotosResponseHolder

interface Repository {
    fun getPhotos(token: String, ownerId: String, offset: String): PhotosResponseHolder?
}