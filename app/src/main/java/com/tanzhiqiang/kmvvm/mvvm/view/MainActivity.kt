package com.tanzhiqiang.kmvvm.mvvm.view

import android.util.Log
import androidx.lifecycle.Observer
import com.tanzhiqiang.kmvvm.R
import com.tanzhiqiang.kmvvm.mvvm.view.base.BaseActivity
import com.tanzhiqiang.kmvvm.mvvm.viewmodel.base.WeatherViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<WeatherViewModel>() {
    override fun layoutId(): Int = R.layout.activity_main
    override fun providerVMClass(): Class<WeatherViewModel> = WeatherViewModel::class.java

    override fun initView() {
        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            mViewModel?.getWeather("成都")
        }
    }

    override fun startObserve() {
        mViewModel?.let {
            it.mWeather.observe(this, Observer { it ->
                Log.i("tt", "天气信息:${it.city}:${it.data.wendu}")
            })
        }
    }
}
