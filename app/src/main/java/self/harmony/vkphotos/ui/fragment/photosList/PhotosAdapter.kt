package self.harmony.vkphotos.ui.fragment.photosList

import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_photo_block.view.*
import self.harmony.vkphotos.R
import self.harmony.vkphotos.base.BaseRecyclerViewAdapter
import self.harmony.vkphotos.base.BaseViewHolder
import self.harmony.vkphotos.base.SimpleOnItemClickListener
import self.harmony.vkphotos.data.PhotoBlock
import self.harmony.vkphotos.util.loadImage

class PhotosAdapter(
        val itemClickListener: SimpleOnItemClickListener<PhotoBlock>,
        override val data: ArrayList<PhotoBlock> = arrayListOf()
) : BaseRecyclerViewAdapter<PhotoBlock>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<PhotoBlock> {
        return PhotoBlockHolder(createView(parent, R.layout.item_photo_block))
    }

    inner class PhotoBlockHolder(private val holder: View) : BaseViewHolder<PhotoBlock>(holder) {

        init {
            holder.setOnClickListener { itemData?.let { itemClickListener.onItemClick(itemData) } }
        }

        override fun bindView(bindObject: PhotoBlock) {
            setImage(bindObject.imageUrl)
            with(holder) {
                if (bindObject.text.isNotEmpty()) {
                    itemText.text = bindObject.text
                }
            }
        }

        override fun release() {
            throw UnsupportedOperationException()
        }

        private fun setImage(imageUrl: String) {
            holder.itemImage.loadImage(imageUrl)
        }
    }
}

