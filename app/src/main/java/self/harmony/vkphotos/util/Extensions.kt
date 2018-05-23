package self.harmony.vkphotos.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import self.harmony.vkphotos.R
import self.harmony.vkphotos.util.imageCache.ImageLoader

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}


fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}


fun ImageView.loadImage(imageUrl: String) {
    ImageLoader.with(context, R.drawable.ic_place_holder)
            .load(imageUrl)
            .into(this)
}

