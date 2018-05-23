//package self.harmony.vkphotos.base
//
//import kotlin.coroutines.experimental.Continuation
//
//class StickyContinuation<ReturnType>
//constructor(
//        private val continuation: Continuation<ReturnType>,
//        private val presenter: BasePresenter<*>) : Continuation<ReturnType> by continuation {
//
//    private var _resumeValue: ReturnType? = null
//    val resumeValue: ReturnType?
//        get() = _resumeValue
//
//    private var _resumeException: Throwable? = null
//    val resumeException: Throwable?
//        get() = _resumeException
//
//    override fun resume(value: ReturnType) {
//        _resumeValue = value
//        presenter.removeStickyContinuation(this)
//        continuation.resume(value)
//    }
//
//    override fun resumeWithException(exception: Throwable) {
//        _resumeException = exception
//        presenter.removeStickyContinuation(this)
//        continuation.resumeWithException(exception)
//    }
//}