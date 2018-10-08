package com.tanzhiqiang.kmvvm.mvvm.viewmodel.base

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), LifecycleObserver, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    private val mLaunchManager: MutableList<Job> = mutableListOf()

    protected fun launchOnUITryCatch(tryBlock: suspend CoroutineScope.() -> Unit,
                                     cacheBlock: suspend CoroutineScope.(Throwable) -> Unit,
                                     finallyBlock: suspend CoroutineScope.() -> Unit,
                                     handleCancellationExceptionManually: Boolean
    ) {
        launchOnUI {
            tryCatch(tryBlock, cacheBlock, finallyBlock, handleCancellationExceptionManually)
        }
    }

    /**
     * add launch task to [mLaunchManager]
     */
    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        val job = launch { block() }
        mLaunchManager.add(job)
        job.invokeOnCompletion { mLaunchManager.remove(job) }

    }

    private suspend fun CoroutineScope.tryCatch(
            tryBlock: suspend CoroutineScope.() -> Unit,
            catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
            finallyBlock: suspend CoroutineScope.() -> Unit,
            handleCancellationExceptionManually: Boolean = false) {
        try {
            tryBlock()
        } catch (e: Throwable) {
            if (e !is CancellationException || handleCancellationExceptionManually) {
                catchBlock(e)
            } else {
                throw e
            }
        } finally {
            finallyBlock()
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory() {
        Log.i("tt", "onDestory")
        clearLaunchTask()
    }

    private fun clearLaunchTask() {
        mLaunchManager.clear()
    }

}