package self.harmony.vkphotos.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import self.harmony.vkphotos.R
import self.harmony.vkphotos.ui.fragment.photosList.PhotosListFragment
import self.harmony.vkphotos.util.hide
import self.harmony.vkphotos.util.show


class MainActivity : AppCompatActivity(), Router, FragmentsUiManager {

    private val fm: FragmentManager by lazy { supportFragmentManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fm.addOnBackStackChangedListener {
            val topFragment = fm.findFragmentById(R.id.mainContainer)
            when (topFragment) {
                is BaseFragment -> {
                    topFragment.view?.requestFocus()
                    isFullScreen(topFragment.isFullScreen())
                }
            }
        }
       navigateToPhotosList()
    }

    private fun replaceTransaction(fragment: BaseFragment): FragmentTransaction {
        val transaction = fm.beginTransaction()
        val topFragment = fm.findFragmentById(R.id.mainContainer) as? BaseFragment
        return if (topFragment?.javaClass == fragment) {
            transaction
        } else {
            transaction.replace(R.id.mainContainer, fragment, fragment.TAG)
            isFullScreen(fragment.isFullScreen())
            println(fragment.TAG)
            transaction
        }
    }

    private fun addTransaction(fragment: BaseFragment): FragmentTransaction {
        isFullScreen(fragment.isFullScreen())
        return fm.beginTransaction()
                .add(R.id.mainContainer, fragment, fragment.TAG)
                .addToBackStack(null)
    }

    private fun onBackPressedInTopFragment(): Boolean {
        val topFragment = fm.findFragmentById(R.id.mainContainer) as BaseFragment
        return topFragment.onBackPressedWillExitCallback()
    }

    override fun onBackPressed() {
        if (onBackPressedInTopFragment())
            super.onBackPressed()
    }

    //#UiManager
    override fun isFullScreen(fullScreenMode: Boolean) {
        if (fullScreenMode) {
            supportActionBar?.hide()
        } else {
            supportActionBar?.show()
        }
    }
    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun showSnackBar(message: String) {
        Snackbar.make(mainContainer, message, Snackbar.LENGTH_LONG).show()
    }
    override fun showLoading() {
        mainProgressBar.show()
    }
    override fun hideLoading() {
        mainProgressBar.hide()
    }


    //#Router
    override fun navigateToPhotosList() {
        replaceTransaction(PhotosListFragment.newInstance()).commit()
    }
}


interface Router {
    fun navigateToPhotosList()
}

interface FragmentsUiManager {
    fun showToast(message: String)
    fun showSnackBar(message: String)
    fun isFullScreen(fullScreenMode: Boolean)
    fun showLoading()
    fun hideLoading()
}