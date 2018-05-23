package self.harmony.vkphotos.data.api

import okhttp3.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url
import self.harmony.vkphotos.data.PhotosResponseHolder


const val PHOTOS = "photos.get"
const val TOKEN = "access_token"
const val OWNER = "owner_id"
const val OFFSET = "offset"

//
interface PhotosApi {

    //https://api.vk.com/method/
    // photos.get?owner_id=7496121&album_id=wall&rev=0&extended=0&offset=0&photo_sizes=0&count=2
    // &access_token=e80113e07580643526bb3bd03d86a1fc44286579497f90305eea8f96ccdbff3f06e0acbbb6c4f5ffb2b3a&v=5.75

//    @GET("$HOST$PHOTOS")
//    fun getPhotos(@QueryMap staticParams: Map<String, String>,
//                  @Query(OWNER) ownerId: String,
//                  @Query(OFFSET) offset: String,
//                  @Query(TOKEN) token: String): Deferred<PhotosResponseHolder>

    @GET(PHOTOS)
    fun getPhotos(@QueryMap staticParams: Map<String, String>,
                  @Query(OWNER) ownerId: String,
                  @Query(OFFSET) offset: String,
                  @Query(TOKEN) token: String): Call<PhotosResponseHolder>

    @GET()
    fun loadPhoto(@Url photoUrl: String): Call<Response>
}