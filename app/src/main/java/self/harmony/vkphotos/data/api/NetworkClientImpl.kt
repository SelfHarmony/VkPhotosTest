package self.harmony.vkphotos.data.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import self.harmony.vkphotos.data.PhotosResponseHolder

class NetworkClientImpl (baseUrl: String) : NetworkClient {
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
                .baseUrl(baseUrl)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        api = retrofit.create(PhotosApi::class.java)
    }

    override fun getPhotos(token: String, ownerId: String, offset: String) : Call<PhotosResponseHolder> {
        return api.getPhotos(
                staticParams = STATIC_QUERY_PARAMS,
                token = token,
                ownerId = ownerId,
                offset = offset)
    }

    override fun loadPhoto(url: String): Call<Response> {
        return api.loadPhoto(url)
    }
}