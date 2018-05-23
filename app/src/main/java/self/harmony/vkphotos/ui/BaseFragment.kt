package self.harmony.vkphotos.ui

import android.support.v4.app.Fragment


abstract class BaseFragment : Fragment(), BaseContract.View {

    override fun showSnackBar(message: String) {
        (activity as? FragmentsUiManager)?.showSnackBar(message)
    }

    var TAG = tag
    override fun showToast(message: String) {
        (activity as? FragmentsUiManager)?.showToast(message)
    }

    override fun showLoading() {
        (activity as? FragmentsUiManager)?.showLoading()
    }

    override fun hideLoading() {
        (activity as? FragmentsUiManager)?.hideLoading()
    }


    open fun isFullScreen(): Boolean = false

    open fun onBackPressedCallback() {}


}
