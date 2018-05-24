package self.harmony.vkphotos.data.api

import okhttp3.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url
import self.harmony.vkphotos.data.PhotosResponseHolder
import self.harmony.vkphotos.data.UserPhotoResponse


const val USER_PHOTOS = "photos.getAll"
const val TOKEN = "access_token"
const val OFFSET = "offset"
const val USER = "user_id"

const val WALL_PHOTOS = "photos.get"
const val OWNER = "owner_id"

interface PhotosApi {


    @GET(WALL_PHOTOS)
    fun getPhotos(@QueryMap staticParams: Map<String, String>,
                  @Query(OWNER) ownerId: String,
                  @Query(OFFSET) offset: String,
                  @Query(TOKEN) token: String): Call<PhotosResponseHolder>


    @GET(USER_PHOTOS)
    fun getUserPhotos(@QueryMap staticParams: Map<String, String>,
                      @Query(OWNER) ownerId: String,
                      @Query(OFFSET) offset: String,
                      @Query(TOKEN) token: String
                      ) :Call<UserPhotoResponse>

    @GET()
    fun loadPhoto(@Url photoUrl: String): Call<Response>
}