package self.harmony.vkphotos.ui.fragment.photosList

import self.harmony.vkphotos.data.PhotoBlock
import self.harmony.vkphotos.data.PhotosRequest
import self.harmony.vkphotos.ui.BaseContract

interface Contract : BaseContract {
    interface view : BaseContract.View {
        fun showPhotos(items: List<PhotoBlock>)
    }
    interface presenter: BaseContract.Presenter{

        fun getPhotosList(params: PhotosRequest)
        fun onPhotoClicked(imageUrl: String)
    }
}