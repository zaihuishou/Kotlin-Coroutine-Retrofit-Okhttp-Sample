package com.tanzhiqiang.kmvvm.mvvm.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.tanzhiqiang.kmvvm.mvvm.viewmodel.base.BaseViewModel

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    protected var mViewModel: VM? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        prepareBeforeInitView()
        setToolbar()
        initView()
        initVM()
        startObserve()
    }

    private fun setToolbar() {
        providerToolBar()?.let { setSupportActionBar(it) }
    }

    /**
     * 布局文件id
     */
    abstract fun layoutId(): Int

    open fun prepareBeforeInitView() {}
    open fun initView() {}
    open fun startObserve() {}

    private fun initVM() {
        providerVMClass()?.let { it ->
            mViewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(mViewModel!!)
        }
    }

    /**
     *设置[Toolbar]
     */
    open fun providerToolBar(): Toolbar? = null

    /**
     * [BaseViewModel]的实现类
     */
    open fun providerVMClass(): Class<VM>? = null


    override fun onDestroy() {
        mViewModel?.let {
            lifecycle.removeObserver(it)
        }

        super.onDestroy()
    }
}