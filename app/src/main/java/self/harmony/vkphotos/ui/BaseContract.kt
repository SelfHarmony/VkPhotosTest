package self.harmony.vkphotos.ui

interface BaseContract{
    interface View {
        fun showToast(message: String)
        fun showSnackBar(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter{

    }
}