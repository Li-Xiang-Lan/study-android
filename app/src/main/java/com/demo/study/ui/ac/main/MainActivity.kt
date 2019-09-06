package com.demo.study.ui.ac.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.demo.study.R
import com.demo.study.ui.ac.home.HomeActivity
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Flowable.intervalRange(1, 3, 0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                startActivity(Intent(this,HomeActivity::class.java))
                finish()
            }.subscribe();
    }
}
