package self.harmony.vkphotos.ui

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import self.harmony.vkphotos.base.CoroutinesUtils.Companion.tryCatch
import self.harmony.vkphotos.base.CoroutinesUtils.Companion.tryCatchFinally
import self.harmony.vkphotos.base.CoroutinesUtils.Companion.tryFinally

abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter {
    var view: V? = null

    fun dropView() {
        this.view = null
    }

    abstract fun onStart()

    abstract fun onStop()

    protected val coroutinesJobs: MutableList<Job> = mutableListOf()

    @Synchronized
    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        val job: Job = launch(UI) { block() }
        coroutinesJobs.add(job)
        job.invokeOnCompletion { coroutinesJobs.remove(job) }
    }

    @Synchronized
    fun launchOnUITryCatch(
            tryBlock: suspend CoroutineScope.() -> Unit,
            catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
            handleCancellationExceptionManually: Boolean) {
        launchOnUI { tryCatch(tryBlock, catchBlock, handleCancellationExceptionManually) }
    }

    @Synchronized
    fun launchOnUITryCatch(
            tryBlock: suspend CoroutineScope.() -> Unit,
            catchBlock: suspend CoroutineScope.(Throwable) -> Unit) {
        launchOnUI { tryCatch(tryBlock, catchBlock, true) }
    }

    @Synchronized
    fun launchOnUITryCatchFinally(
            tryBlock: suspend CoroutineScope.() -> Unit,
            catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
            finallyBlock: suspend CoroutineScope.() -> Unit,
            handleCancellationExceptionManually: Boolean) {
        launchOnUI { tryCatchFinally(tryBlock, catchBlock, finallyBlock, handleCancellationExceptionManually) }
    }

    @Synchronized
    fun launchOnUITryFinally(
            tryBlock: suspend CoroutineScope.() -> Unit,
            finallyBlock: suspend CoroutineScope.() -> Unit,
            suppressCancellationException: Boolean) {
        launchOnUI { tryFinally(tryBlock, finallyBlock, suppressCancellationException) }
    }

    @Synchronized
    fun cancelAllCoroutines() {
        val coroutinesJobsSize = coroutinesJobs.size

        if (coroutinesJobsSize > 0) {
            for (i in coroutinesJobsSize - 1 downTo 0) {
                coroutinesJobs[i].cancel()
            }
        }
    }
}
