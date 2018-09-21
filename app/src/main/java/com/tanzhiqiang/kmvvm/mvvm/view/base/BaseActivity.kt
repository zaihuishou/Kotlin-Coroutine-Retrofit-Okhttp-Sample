package com.tanzhiqiang.kmvvm.mvvm.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.tanzhiqiang.kmvvm.mvvm.viewmodel.base.BaseViewModel
import kotlinx.android.extensions.CacheImplementation
import kotlinx.android.extensions.ContainerOptions

@ContainerOptions(CacheImplementation.SPARSE_ARRAY)
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    protected var mViewModel: VM? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        prepareBeforeView()
        initView()
        initVM()
    }

    /**
     * 布局文件id
     */
    abstract fun layoutId(): Int

    open fun prepareBeforeView() {}
    open fun initView() {}

    private fun initVM() {
        providerVMClass()?.let { it ->
            mViewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(mViewModel!!)

        }
    }

    /**
     * [BaseViewModel]的实现类
     */
    open fun providerVMClass(): Class<VM>? = null


    override fun onDestroy() {
        mViewModel?.let {
            lifecycle.removeObserver(it)
            it.clearLaunchTask()
        }

        super.onDestroy()
    }
}