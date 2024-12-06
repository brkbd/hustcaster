package com.hustcaster.app.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import okio.Closeable
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class SafeCoroutineScopeImpl(
    val context: CoroutineContext,
    private val errorHandler: ((Throwable) -> Unit)? = null
) : CoroutineScope, Closeable {
    override val coroutineContext: CoroutineContext
        get() = context + UncaughtCoroutineExceptionHandler(errorHandler)

    override fun close() {
        context.cancelChildren()
    }
}

private class UncaughtCoroutineExceptionHandler(private val errorHandler: ((Throwable) -> Unit)? = null) :
    CoroutineExceptionHandler, AbstractCoroutineContextElement(
    CoroutineExceptionHandler.Key
) {
    override fun handleException(context: CoroutineContext, exception: Throwable) {
        exception.printStackTrace()
        errorHandler?.let { it(exception) }
    }

}

@Suppress("FunctionName")
fun SafeCoroutineScope(context: CoroutineContext, errorHandler: ((Throwable) -> Unit)? = null) =
    SafeCoroutineScopeImpl(context, errorHandler)
