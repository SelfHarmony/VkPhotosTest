package self.harmony.vkphotos.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import self.harmony.vkphotos.api.NetworkConfig.HOST
import self.harmony.vkphotos.api.models.PhotosResponseHolder

class NetworkModule {
    private var api: PhotosApi
    val STATIC_QUERY_PARAMS = hashMapOf(
            Pair("album_id", "wall"),
            Pair("rev", "0"),
            Pair("extended", "0"),
            Pair("photo_sizes", "0"),
            Pair("count", "20"),
            Pair("v", "5.75"))

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(HOST)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        api = retrofit.create(PhotosApi::class.java)
    }

    fun getPhotos(token: String, ownerId: String, offset: String) : Deferred<PhotosResponseHolder> {
        return api.getPhotos(
                staticParams = STATIC_QUERY_PARAMS,
                token = token,
                ownerId = ownerId,
                offset = offset)
    }
}