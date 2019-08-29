package com.demo.study.ui.ac.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.demo.study.R
import com.demo.study.bean.CourseTyepBean
import com.demo.study.retrofit.ApiController
import com.demo.study.retrofit.ObserverOnNextListener
import com.demo.study.retrofit.RxSchedulers
import com.demo.study.retrofit.observer.CommonObserver

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ApiController.service
            .getCourseType()
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeListData())
            .subscribe(CommonObserver(object: ObserverOnNextListener<List<CourseTyepBean>> {
                override fun onNext(t: List<CourseTyepBean>) {
                    t.forEach {  Log.e("qwer",it.title)}
                }

                override fun onError(e: Throwable) {
                    Log.e("qwer",e.message)
                }
            }))
    }
}
