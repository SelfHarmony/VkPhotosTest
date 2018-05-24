package self.harmony.vkphotos.data.api

import okhttp3.Response
import retrofit2.Call
import self.harmony.vkphotos.data.PhotosResponseHolder
import self.harmony.vkphotos.data.UserPhotoResponse

interface NetworkClient {
    fun getPhotos(token: String, ownerId: String, offset: String) : Call<PhotosResponseHolder>
    fun loadPhoto(url: String): Call<Response>
    fun getUserPhotos(token: String, ownerId: String, offset: String): Call<UserPhotoResponse>
}
