package com.demo.study

import android.app.Application
import android.content.Context
import org.litepal.LitePal

/**
 * Created by cheng on 2019/6/24.
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        LitePal.initialize(this)
    }

    companion object {
        var context: Context? = null
            private set
    }
}
