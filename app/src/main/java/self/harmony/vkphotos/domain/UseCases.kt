package self.harmony.vkphotos.domain

import self.harmony.vkphotos.data.PhotoBlocksData
import self.harmony.vkphotos.data.PhotosRequest
import self.harmony.vkphotos.data.UserPhotoResponse
import self.harmony.vkphotos.data.repository.Repository
import self.harmony.vkphotos.domain.mapper.UserPhotoMapper

class PhotosUseCase(private val repo: Repository): BaseUseCase<PhotosRequest, PhotoBlocksData>() {

    override suspend fun execute(params: PhotosRequest): PhotoBlocksData {
        val response: UserPhotoResponse? = asyncAwait {
            repo.getUserPhotos(
                    params.token,
                    params.ownerId,
                    params.offset)
        }
        return UserPhotoMapper.fromNetwork(response)
    }

}
