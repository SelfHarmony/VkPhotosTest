package self.harmony.vkphotos.domain

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Deferred

interface UseCase<T, R> {
    fun updateParameter(parameter: T)
    suspend fun execute(params: T): R

    suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T>
    suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T): T
    fun cancelAllAsync()
    fun cleanup()
}
