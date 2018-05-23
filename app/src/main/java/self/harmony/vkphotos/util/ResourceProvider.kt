package self.harmony.vkphotos.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable

@SuppressLint("StaticFieldLeak")

object ResourceProvider {

    private var context: Context? = null

    fun init(context: Context) {
        this.context = context
    }

    fun getString(resId: Int): String {
        context?.let { context ->
            return context.resources.getString(resId)
        } ?: throw IllegalStateException("ResourceProvider not initialized")
    }

    fun getDrawable(resId: Int): Drawable {
        context?.let { context ->
            return context.resources.getDrawable(resId)
        } ?: throw IllegalStateException("ResourceProvider not initialized")
    }

    fun getColor(resId: Int): Int {
        context?.let { context ->
            return context.resources.getColor(resId)
        } ?: throw IllegalStateException("ResourceProvider not initialized")
    }

    fun getStringArray(resId: Int): Array<String> {
        context?.let { context ->
            return context.resources.getStringArray(resId)
        } ?: throw IllegalStateException("ResourceProvider not initialized")
    }

    fun getDimen(resId: Int): Float {
        context?.let { context ->
            return context.resources.getDimension(resId)
        } ?: throw IllegalStateException("ResourceProvider not initialized")
    }
}