package self.harmony.vkphotos.ui.fragment.photosList

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_photos_list.*
import self.harmony.vkphotos.R
import self.harmony.vkphotos.base.OnBottomReachedListener
import self.harmony.vkphotos.base.SimpleOnItemClickListener
import self.harmony.vkphotos.core.App
import self.harmony.vkphotos.core.Constants.APP_PREFERENCES
import self.harmony.vkphotos.core.Constants.TOKEN_KEY
import self.harmony.vkphotos.core.Constants.USER_KEY
import self.harmony.vkphotos.data.PhotoBlock
import self.harmony.vkphotos.data.PhotoBlocksData
import self.harmony.vkphotos.data.PhotosRequest
import self.harmony.vkphotos.data.repository.Repository
import self.harmony.vkphotos.di.PerFragment
import self.harmony.vkphotos.domain.PhotosUseCase
import self.harmony.vkphotos.domain.UseCase
import self.harmony.vkphotos.ui.BaseFragment
import self.harmony.vkphotos.util.hide
import self.harmony.vkphotos.util.show
import javax.inject.Inject

class PhotosListFragment : BaseFragment(), Contract.view {
    @Inject
    lateinit var presenter: PhotosListPresenter

    lateinit var component: Component
    private val photoPreviewPagerAdapter: PhotoPreviewPagerAdapter by lazy { PhotoPreviewPagerAdapter(context)}

    private val photosAdapter by lazy {
        PhotosAdapter(
                itemClickListener = object : SimpleOnItemClickListener<PhotoBlock> {
                    override fun onItemClick(itemObject: PhotoBlock?) {
                        itemObject?.let {
                            onPhotoClicked(it)
                        }
                    }
                })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        presenter.view = this
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_photos_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val prefs = activity.application.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val token = prefs.getString(TOKEN_KEY, "")
        val userId = prefs.getString(USER_KEY, "")
        setView(token, userId)
        presenter.getPhotosList(PhotosRequest(token, userId, "0"))
    }

    private fun setView(token: String, userId: String) {
        photosListRecyclerView.adapter = photosAdapter
        photosListRecyclerView.layoutManager = GridLayoutManager(context, 2)
        photoPreviewPager.offscreenPageLimit = 6
        photoPreviewPager.adapter = photoPreviewPagerAdapter
        photosAdapter.setOverscrollListener(overscrollListener = object : OnBottomReachedListener {
            override fun onBottomReached(position: Int) {
                presenter.getPhotosList(PhotosRequest(token, userId, position.toString()))
            }
        })
        photoPreviewPagerAdapter.setOverscrollListener(overscrollListener = object : OnBottomReachedListener {
            override fun onBottomReached(position: Int) {
                presenter.getPhotosList(PhotosRequest(token, userId, position.toString()))
            }
        })
    }

    override fun onBackPressedWillExitCallback(): Boolean {
        return if (photoPreviewPager.visibility == View.VISIBLE) {
            photoPreviewPager.hide()
            false
        } else {
            true
        }
    }

    //region Dagger inject
    fun inject() {
        val appComponent = (activity?.application as App).appComponent
        component = DaggerPhotosListFragment_Component.builder().module(Module()).component(appComponent).build()
        component.inject(this)
    }

    @PerFragment
    @dagger.Component(
            modules = [(Module::class)],
            dependencies = [(App.Component::class)]
    )
    interface Component : App.Component {
        fun inject(fragment: PhotosListFragment)
        fun presenter(): PhotosListPresenter
    }


    @dagger.Module
    class Module {
        @Provides
        @PerFragment
        fun providePhotosListPresenter(
                getPhotosUseCase: UseCase<PhotosRequest, PhotoBlocksData>): PhotosListPresenter {
            return PhotosListPresenter(getPhotosUseCase)
        }

        @Provides
        @PerFragment
        fun provideGetPhotosUseCase(repo: Repository): UseCase<PhotosRequest, PhotoBlocksData> {
            return PhotosUseCase(repo)
        }

    }


    //endregion

    private fun onPhotoClicked(photoBlock: PhotoBlock) {
        photoPreviewPager.setCurrentItem(photosAdapter.getPosition(photoBlock), false)
        photoPreviewPager.show()
    }

    override fun showMorePhotos(items: List<PhotoBlock>) {
        photosAdapter.addMoreToEnd(items)
        photoPreviewPagerAdapter.addMoreToEnd(items)
    }

    companion object {
        val TAG = "PhotosListFragment"
        fun newInstance(): BaseFragment {
            val fragment = PhotosListFragment()
            return fragment
        }
    }
}