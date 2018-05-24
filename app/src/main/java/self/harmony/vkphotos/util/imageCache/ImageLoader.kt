package self.harmony.vkphotos.util.imageCache

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.LruCache
import android.widget.ImageView
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import self.harmony.vkphotos.base.CoroutinesUtils.Companion.tryCatch
import java.net.HttpURLConnection
import java.net.URL

@SuppressLint("StaticFieldLeak")

object ImageLoader : ComponentCallbacks2 {
    private var context: Context? = null
    private var cache: TCLruCache? = null
    private var image: Bitmap? = null
    private var url: String = ""
    private var defaultResource: Int = 0
    private val coroutinesJobs: MutableList<Job> = mutableListOf()


    fun with(context: Context?, defaultRes: Int): ImageLoader {
        this.context = context
        this.defaultResource = defaultRes
        val am = context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val maxKb = am.memoryClass * 1024
        val limitKb = maxKb / 6 //  max ram
        cache = if (cache == null) TCLruCache(limitKb) else cache
        return this
    }

    fun load(url: String): ImageLoader {
        image = cache?.get(url)
        this.url = url
        return this
    }


    fun into(imageView: ImageView) {
        imageView.setImageResource(defaultResource)
        if (cache?.get(url) != null) {
            imageView.setImageBitmap(image)
        } else {
            setImage(imageView, url)
        }
    }

    private fun setImage(imageView: ImageView, url: String) {
        launchOnUITryCatch({
            val bmp = getBitmapFromURL(url)
            if (bmp != null) {
                cache?.put(url, bmp)
                imageView.setImageBitmap(bmp)
            }
        }, { error ->
            Log.e("IMAGE_LOADER", error.message)
        })
    }


    private suspend fun getBitmapFromURL(src: String): Bitmap? {
        return asyncAwait({
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            val myBitmap = BitmapFactory.decodeStream(input)
            myBitmap
        })
    }


    override fun onConfigurationChanged(newConfig: Configuration) {}

    override fun onLowMemory() {}

    override fun onTrimMemory(level: Int) {
        if (level >= ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
            cache?.evictAll()
        } else if (level >= ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
            cache?.trimToSize(cache?.size()!! / 2)
        }
    }

    @Synchronized
    suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> {
        val deferred: Deferred<T> = async(CommonPool) { block() }
        return deferred
    }

    @Synchronized
    suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T): T {
        return async(block).await()
    }

    @Synchronized
    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        val job: Job = launch(UI) { block() }
        ImageLoader.coroutinesJobs.add(job)
        job.invokeOnCompletion { ImageLoader.coroutinesJobs.remove(job) }
    }

    @Synchronized
    fun launchOnUITryCatch(
            tryBlock: suspend CoroutineScope.() -> Unit,
            catchBlock: suspend CoroutineScope.(Throwable) -> Unit) {
        launchOnUI { tryCatch(tryBlock, catchBlock, true) }
    }
}


private class TCLruCache(maxSize: Int) : LruCache<String, Bitmap>(maxSize) {

    override fun sizeOf(key: String, value: Bitmap): Int {
        val kbOfBitmap = value.byteCount / 1024
        return kbOfBitmap
    }
}

