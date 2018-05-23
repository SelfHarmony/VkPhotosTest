package self.harmony.vkphotos.core

import android.app.Application
import android.content.Intent
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk
import self.harmony.vkphotos.data.repository.Repository
import self.harmony.vkphotos.di.MainModule
import self.harmony.vkphotos.ui.LoginActivity
import self.harmony.vkphotos.util.ResourceProvider
import javax.inject.Singleton


class App : Application() {
    var appComponent: Component? = null
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
        appComponent = createAppComponent()
        ResourceProvider.init(this.applicationContext)
        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(this)
    }

    private fun createAppComponent(): Component {
        return DaggerApp_Component.builder().mainModule((MainModule(this))).build()
    }

    @Singleton
    @dagger.Component(modules = [(MainModule::class)])
    interface Component {
        fun repo(): Repository
    }

}