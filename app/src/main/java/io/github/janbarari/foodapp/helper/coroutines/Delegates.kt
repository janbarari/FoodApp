package io.github.janbarari.foodapp.helper.coroutines

import kotlinx.coroutines.*

/**
 * Lazy job runner
 */
fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}