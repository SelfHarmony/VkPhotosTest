package self.harmony.vkphotos.base

import android.support.v7.widget.RecyclerView
import android.view.View



abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    protected var itemData: T? = null

    fun bindData(bindObject: T) {
        itemData = bindObject
        bindView(bindObject)
    }

    protected abstract fun bindView(bindObject: T)

    protected abstract fun release()
}
