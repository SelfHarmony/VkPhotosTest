package self.harmony.vkphotos.api

import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import self.harmony.vkphotos.api.NetworkConfig.HOST
import self.harmony.vkphotos.api.models.PhotosResponseHolder


const val PHOTOS = "photos.get"
const val TOKEN = "access_token"
const val OWNER = "owner_id"
const val OFFSET = "offset"

//
interface PhotosApi {

    //https://api.vk.com/method/
    // photos.get?owner_id=7496121&album_id=wall&rev=0&extended=0&offset=0&photo_sizes=0&count=2
    // &access_token=e80113e07580643526bb3bd03d86a1fc44286579497f90305eea8f96ccdbff3f06e0acbbb6c4f5ffb2b3a&v=5.75

    @GET("$HOST$PHOTOS")
    fun getPhotos(@QueryMap staticParams: Map<String, String>,
                  @Query(OWNER) ownerId: String,
                  @Query(OFFSET) offset: String,
                  @Query(TOKEN) token: String): Deferred<PhotosResponseHolder>
}