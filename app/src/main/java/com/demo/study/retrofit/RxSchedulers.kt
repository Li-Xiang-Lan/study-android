package com.demo.study.retrofit

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by cheng on 2019/3/29.
 */
class RxSchedulers {

    companion object {
        fun <T> io_main(): ObservableTransformer<T, T> = ObservableTransformer { it.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
    }
}