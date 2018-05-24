package self.harmony.vkphotos.ui.fragment.photosList

import self.harmony.vkphotos.data.PhotoBlocksData
import self.harmony.vkphotos.data.PhotosRequest
import self.harmony.vkphotos.domain.UseCase
import self.harmony.vkphotos.ui.BasePresenter

class PhotosListPresenter(private val getPhotosUseCase: UseCase<PhotosRequest, PhotoBlocksData>) : BasePresenter<Contract.view>(), Contract.presenter {
    override fun onStart() {}
    override fun onStop() {}

    override fun getPhotosList(params: PhotosRequest) {
        view?.showLoading()
        launchOnUITryCatch ({
            val photosData = getPhotosUseCase.execute(params)
            view?.showMorePhotos(photosData.items)
        }, { error ->
            error.message?.let { view?.showSnackBar(it)}
        })
    }

    override fun onPhotoClicked(imageUrl: String) {

    }
}