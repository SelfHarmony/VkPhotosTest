package self.harmony.vkphotos.data.repository

import self.harmony.vkphotos.data.UserPhotoResponse
import self.harmony.vkphotos.data.api.NetworkClient

class RepositoryImpl(val networkClient: NetworkClient) : Repository {
    override fun getUserPhotos(token: String, ownerId: String, offset: String): UserPhotoResponse? {
        return networkClient.getUserPhotos(token, ownerId, offset)
                .execute()
                .body()
    }
}