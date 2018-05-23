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
import javax.inject.Inject

class PhotosListFragment : BaseFragment(), Contract.view {
    @Inject lateinit var presenter: PhotosListPresenter

    lateinit var component: Component
    private val photosAdapter by lazy {
        PhotosAdapter(object : SimpleOnItemClickListener<PhotoBlock> {
            override fun onItemClick(itemObject: PhotoBlock?) {
                itemObject?.let {
                    presenter.onPhotoClicked(it.imageUrl)
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
        setView()
        presenter.getPhotosList(PhotosRequest(token, userId, "0"))
    }

    private fun setView() {
        photosListRecyclerView.adapter = photosAdapter
        photosListRecyclerView.layoutManager = GridLayoutManager(context, 2)
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


    override fun showPhotos(items: List<PhotoBlock>) {
        photosAdapter.setData(items)
    }

    companion object {
        val TAG = "PhotosListFragment"
        fun newInstance(): BaseFragment {
            val fragment = PhotosListFragment()
            return fragment
        }
    }
}