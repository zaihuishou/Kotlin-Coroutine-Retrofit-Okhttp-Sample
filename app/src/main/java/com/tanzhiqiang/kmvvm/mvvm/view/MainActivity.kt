package com.tanzhiqiang.kmvvm.mvvm.view

import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.tanzhiqiang.kmvvm.R
import com.tanzhiqiang.kmvvm.mvvm.view.base.BaseActivity
import com.tanzhiqiang.kmvvm.mvvm.viewmodel.base.WeatherViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity<WeatherViewModel>() {
    override fun layoutId(): Int = R.layout.activity_main
    override fun providerVMClass(): Class<WeatherViewModel> = WeatherViewModel::class.java

    override fun providerToolBar(): Toolbar = toolbar
    override fun initView() {
        fab.setOnClickListener {
            mViewModel?.getWeather(
                    {
                        progress_bar.visibility = View.VISIBLE
                    },
                    {
                        tv_hello.visibility = View.VISIBLE
                        progress_bar.visibility = View.GONE
                    })
        }
    }

    override fun startObserve() {
        mViewModel?.apply {
            mWeather.observe(this@MainActivity, Observer { it ->
                tv_hello.text = "${it.data}"

            })
        }
    }

    override fun onDestroy() {
        Log.i("tt", "MainActivity onDestory")
        super.onDestroy()
    }
}
