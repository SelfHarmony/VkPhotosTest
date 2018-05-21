package self.harmony.vkphotos.ui

import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import self.harmony.vkphotos.R
import self.harmony.vkphotos.api.NetworkModule


class MainActivity : AppCompatActivity() {

    val networkClient: NetworkModule by lazy { NetworkModule() }
    private val fm: FragmentManager by lazy {
        fragmentManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fm.addOnBackStackChangedListener {
            val topFragment = fm.findFragmentById(R.id.mainContainer)
            when (topFragment) {
                is Fragment -> {
                    topFragment.view?.requestFocus()
                }
            }
        }
    }

    private fun replaceTransaction(fragment: Fragment): FragmentTransaction {
        val transaction = fm.beginTransaction()
        val topFragment = fm.findFragmentById(R.id.mainContainer)
        return if (topFragment?.javaClass == fragment.javaClass) {
            transaction
        } else {
            fm.popBackStack()
            transaction.replace(R.id.mainContainer, fragment)
            transaction.addToBackStack(null)
        }
    }

    private fun addTransaction(fragment: Fragment): FragmentTransaction {
        return fm.beginTransaction()
                .add(R.id.mainContainer, fragment)
                .addToBackStack(null)
    }

}
