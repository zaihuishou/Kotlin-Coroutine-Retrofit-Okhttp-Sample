package com.tanzhiqiang.kmvvm.mvvm.viewmodel.base

import androidx.lifecycle.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main

open class BaseViewModel : ViewModel(), LifecycleObserver {

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
        val job = launch(Dispatchers.Main) { block() }
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
        clearLaunchTask()
    }

    fun clearLaunchTask() {
        mLaunchManager.clear()
    }
}