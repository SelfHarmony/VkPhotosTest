package self.harmony.vkphotos.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {
    open val data = ArrayList<T>()

    override fun onBindViewHolder(holder: BaseViewHolder<T>?, position: Int) {
        val data = data[position]
        holder?.bindData(data)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun createView(parent: ViewGroup?, layoutResId: Int): View {
        val layoutInflater = LayoutInflater.from(parent?.context)
        return layoutInflater.inflate(layoutResId, parent, false)
    }

    fun setData(dataList: List<T>) {
        data.clear()
        data.addAll(dataList)
        notifyDataSetChanged()
    }

    fun getSize(): Int {
        return data.size
    }

    fun getByPosition(position: Int): T {
        return data[position]
    }

    fun getPosition(item: T): Int {
        return data.indexOf(item)
    }

    fun addMoreToEnd(itemMessages: List<T>) {
        val startRangePosition = data.size
        data.addAll(itemMessages)
        notifyItemRangeInserted(startRangePosition, itemMessages.size)
    }

}