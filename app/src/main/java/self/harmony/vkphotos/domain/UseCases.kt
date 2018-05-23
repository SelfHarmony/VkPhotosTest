package self.harmony.vkphotos.domain

import self.harmony.vkphotos.data.PhotoBlocksData
import self.harmony.vkphotos.data.PhotosRequest
import self.harmony.vkphotos.data.PhotosResponseHolder
import self.harmony.vkphotos.data.repository.Repository
import self.harmony.vkphotos.domain.mapper.PhotoMapper

class PhotosUseCase(private val repo: Repository): BaseUseCase<PhotosRequest, PhotoBlocksData>() {

    override suspend fun execute(params: PhotosRequest): PhotoBlocksData {
        val photosHolder: PhotosResponseHolder? = asyncAwait {
            repo.getPhotos(
                    params.token,
                    params.ownerId,
                    params.offset)
        }
        return PhotoMapper.fromNetwork(photosHolder?.response)
    }

}
