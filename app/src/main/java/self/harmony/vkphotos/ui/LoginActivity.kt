package self.harmony.vkphotos.ui

import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.runBlocking
import self.harmony.vkphotos.R
import self.harmony.vkphotos.api.NetworkModule
import self.harmony.vkphotos.api.models.PhotosResponseHolder

class LoginActivity : AppCompatActivity() {

    val networkClient: NetworkModule by lazy { NetworkModule() }
    private val fm: FragmentManager by lazy {
        fragmentManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        VKSdk.login(this, "photos")
        fm.addOnBackStackChangedListener {
            val topFragment = fm.findFragmentById(R.id.loginContainer)
            when (topFragment) {
                is Fragment -> {
                    topFragment.view?.requestFocus()
                }
            }
        }
//        val fingerprints = VKUtil.getCertificateFingerprint(this, this.packageName)
    }

    private fun replaceTransaction(fragment: Fragment): FragmentTransaction {
        val transaction = fm.beginTransaction()
        val topFragment = fm.findFragmentById(R.id.loginContainer)
        return if (topFragment?.javaClass == fragment.javaClass) {
            transaction
        } else {
            fm.popBackStack()
            transaction.replace(R.id.loginContainer, fragment)
            transaction.addToBackStack(null)
        }
    }

    private fun addTransaction(fragment: Fragment): FragmentTransaction {
        return fm.beginTransaction()
                .add(R.id.loginContainer, fragment)
                .addToBackStack(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {

                    var result = runBlocking {
                        return@runBlocking getPhotos(res).await()
                        }.response
                        Toast.makeText(this@LoginActivity, result?.count.toString(), Toast.LENGTH_LONG).show()
                    }
                    override fun onError(error: VKError) {}
                })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    suspend fun getPhotos(res: VKAccessToken): Deferred<PhotosResponseHolder> {
        return networkClient.getPhotos(res.accessToken, res.userId, "0")
    }

}