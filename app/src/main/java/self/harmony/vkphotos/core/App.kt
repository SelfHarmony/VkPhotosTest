package self.harmony.vkphotos.core

import android.app.Application
import android.content.Intent
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk
import self.harmony.vkphotos.ui.LoginActivity


class App : Application() {

    var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onCreate() {
        super.onCreate()
//        DI.init(this.applicationContext)
        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(this)
    }







}