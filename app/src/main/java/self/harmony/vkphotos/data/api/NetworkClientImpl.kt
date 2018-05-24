package self.harmony.vkphotos.data.api

import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import self.harmony.vkphotos.data.PhotosResponseHolder
import self.harmony.vkphotos.data.UserPhotoResponse

class NetworkClientImpl(baseUrl: String) : NetworkClient {
    private var api: PhotosApi
    val WALL_REQUEST_PARAMS = hashMapOf(
            Pair("album_id", "wall"),
            Pair("rev", "0"),
            Pair("extended", "0"),
            Pair("photo_sizes", "0"),
            Pair("count", "20"),
            Pair("v", "5.75"))

    val USER_REQUEST_PARAMS = hashMapOf(
            Pair("extended", "1"),
            Pair("photo_sizes", "1"),
            Pair("need_hidden", "0"),
            Pair("skip_hidden", "1"),
            Pair("count", "20"),
            Pair("v", "5.77"))

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        api = retrofit.create(PhotosApi::class.java)
    }

    override fun getPhotos(token: String, ownerId: String, offset: String): Call<PhotosResponseHolder> {
        return api.getPhotos(
                staticParams = WALL_REQUEST_PARAMS,
                token = token,
                ownerId = ownerId,
                offset = offset)
    }

    override fun getUserPhotos(token: String, ownerId: String, offset: String): Call<UserPhotoResponse> {
        return api.getUserPhotos(
                staticParams = USER_REQUEST_PARAMS,
                token = token,
                ownerId = ownerId,
                offset = offset)
    }

    override fun loadPhoto(url: String): Call<Response> {
        return api.loadPhoto(url)
    }
}