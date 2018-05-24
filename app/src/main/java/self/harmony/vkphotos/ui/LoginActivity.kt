package self.harmony.vkphotos.ui

import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import self.harmony.vkphotos.R
import self.harmony.vkphotos.core.Constants.APP_PREFERENCES
import self.harmony.vkphotos.core.Constants.PHOTOS_SCOPE
import self.harmony.vkphotos.core.Constants.TOKEN_KEY
import self.harmony.vkphotos.core.Constants.USER_KEY

class LoginActivity : AppCompatActivity() {

    private val fm: FragmentManager by lazy {
        fragmentManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        VKSdk.login(this, PHOTOS_SCOPE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {
                        val prefs = application.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                        prefs.edit().putString(TOKEN_KEY, res.accessToken).apply()
                        prefs.edit().putString(USER_KEY, res.userId).apply()
                        navigateToMain()
//                        val photosJob: Job = launchAsync(getUserPhotos(res))
//
//                            runAsync(getUserPhotos(res), {result ->
//                            Toast.makeText(this@LoginActivity, result?.response?.count.toString(), Toast.LENGTH_LONG).show()
//                        })?.response
//                        val photoRequest = photosResponse?.items?.get(0)?.photo_604?.let { networkClient.loadPhoto(it) }
//
//                        runAsync(photoRequest, {result: Response? ->
//                            val stream = result?.body()?.byteStream()
//                            val bmp = BitmapFactory.decodeStream(stream)
//                            testImage.setImageBitmap(bmp)
//                        })

                    }

                    override fun onError(error: VKError) {}
                })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
