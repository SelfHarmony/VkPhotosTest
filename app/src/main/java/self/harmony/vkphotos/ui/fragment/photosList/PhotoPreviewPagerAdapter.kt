package self.harmony.vkphotos.ui.fragment.photosList

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_photo_preview.view.*
import self.harmony.vkphotos.R
import self.harmony.vkphotos.base.OnBottomReachedListener
import self.harmony.vkphotos.data.PhotoBlock
import self.harmony.vkphotos.util.loadImage


class PhotoPreviewPagerAdapter(private val mContext: Context?) : PagerAdapter() {
    private var data: MutableList<PhotoBlock> = mutableListOf()
    private var onBottomReachedListener: OnBottomReachedListener? = null

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(R.layout.item_photo_preview, collection, false)
        collection.addView(layout)
        bindData(data[position], layout)
        if (position == data.size - 1){
            onBottomReachedListener?.onBottomReached(data.size + 1)
        }
        return layout
    }

    private fun bindData(photo: PhotoBlock, layout: View?) {
        with(layout?.let { it }!!) {
            photoPreviewImageView
                    ?.loadImage(photo.largeImageUrl)
            likesTextView.text = photo.likes.toString()
            repostsTextView.text = photo.reposts.toString()
            if ( photo.text.isNotEmpty()) {
                descriptionTextView.text = photo.text
            }
        }
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
        notifyDataSetChanged()
    }

    override fun getCount(): Int = data.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun getPageTitle(position: Int): CharSequence? {
        return data[position].text
    }

    fun setData(dataList: List<PhotoBlock>) {
        data.clear()
        data.addAll(dataList)
        notifyDataSetChanged()
    }

    fun addMoreToEnd(itemMessages: List<PhotoBlock>) {
        data.addAll(itemMessages)
        notifyDataSetChanged()
    }


    fun setOverscrollListener(overscrollListener: OnBottomReachedListener) {
        this.onBottomReachedListener = overscrollListener
    }
}

