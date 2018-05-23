package self.harmony.vkphotos.data.repository

import self.harmony.vkphotos.data.api.NetworkClient
import self.harmony.vkphotos.data.PhotosResponseHolder

class RepositoryImpl(val networkClient: NetworkClient) : Repository {
    override fun getPhotos(token: String, ownerId: String, offset: String): PhotosResponseHolder? {
        return networkClient.getPhotos(token, ownerId, offset)
                .execute()
                .body()
    }
}