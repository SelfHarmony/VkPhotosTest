package self.harmony.vkphotos.data.repository

import self.harmony.vkphotos.data.UserPhotoResponse

interface Repository {
    fun getUserPhotos(token: String, ownerId: String, offset: String): UserPhotoResponse?
}