package self.harmony.vkphotos.caching

import android.app.ActivityManager
import android.content.ComponentCallbacks2
import android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND
import android.content.ComponentCallbacks2.TRIM_MEMORY_MODERATE
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.LruCache
import android.widget.ImageView
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class TCImageLoader(context: Context) : ComponentCallbacks2 {
    private val cache: TCLruCache

    init {
        val am = context.getSystemService(
                Context.ACTIVITY_SERVICE) as ActivityManager
        val maxKb = am.memoryClass * 1024
        val limitKb = maxKb / 8 // 1/8 ram
        cache = TCLruCache(limitKb)
    }

    fun display(url: String, imageView: ImageView, defaultResource: Int) {
        imageView.setImageResource(defaultResource)
        val image = cache.get(url)
        if (image != null) {
            imageView.setImageBitmap(image)
        } else {
            SetImageTask(imageView).execute(url)
        }
    }

    private inner class TCLruCache(maxSize: Int) : LruCache<String, Bitmap>(maxSize) {

        override fun sizeOf(key: String, value: Bitmap): Int {
            val kbOfBitmap = value.byteCount / 1024
            return kbOfBitmap
        }
    }

    private inner class SetImageTask(private val imageview: ImageView) : AsyncTask<String, Void, Int>() {
        private var bmp: Bitmap? = null

        override fun doInBackground(vararg params: String): Int? {
            val url = params[0]
            try {
                bmp = getBitmapFromURL(url)
                if (bmp != null) {
                    cache.put(url, bmp)
                } else {
                    return 0
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return 0
            }

            return 1
        }

        override fun onPostExecute(result: Int?) {
            if (result == 1) {
                imageview.setImageBitmap(bmp)
            }
            super.onPostExecute(result)
        }

        private fun getBitmapFromURL(src: String): Bitmap? {
            return try {
                val url = URL(src)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input = connection.inputStream
                val myBitmap = BitmapFactory.decodeStream(input)
                myBitmap
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {}

    override fun onLowMemory() {}

    override fun onTrimMemory(level: Int) {
        if (level >= TRIM_MEMORY_MODERATE) {
            cache.evictAll()
        } else if (level >= TRIM_MEMORY_BACKGROUND) {
            cache.trimToSize(cache.size() / 2)
        }
    }
}