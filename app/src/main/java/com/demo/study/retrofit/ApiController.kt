package com.demo.study.retrofit

import android.content.Intent
import com.demo.study.MyApp
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by cheng on 2019/3/28.
 */
object ApiController{
    val service: ApiService by lazy (LazyThreadSafetyMode.SYNCHRONIZED){
        getRetrofit().create(ApiService::class.java)
    }

    private fun getRetrofit(): Retrofit {
        // 获取retrofit的实例
        return Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    }

    private fun getOkHttpClient(): OkHttpClient {
        //添加一个log拦截器,打印所有的log
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //可以设置请求过滤的水平,body,basic,headers
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        //自定义OkHttpClient
        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MyApp.context))
        return OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                .addInterceptor(HeaderInterceptor())
                .connectTimeout(20L, TimeUnit.SECONDS)
                .readTimeout(20L, TimeUnit.SECONDS)
                .writeTimeout(20L, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
    }

    fun <T> judgeData(clazz: Class<T>): Function<ResponseModel<T>, ObservableSource<T>>{
        return Function { (status, errorMsg, data) -> Observable.create {
            when(status){
            //登录失败
                100->{
//                    SPUtil.getInstance()?.clearUserInfo()
//                    EventBus.getDefault().post(EventBusBean(CodeUtil.LOGOUT_SUCCESS))
//                    val intent = Intent(MyApp.context, LoginActivity::class.java)
//                    intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
//                    MyApp.context?.startActivity(intent)
//                    it.onError(Error(errorMessage+"(603)"))
                }
                1->{
                    if (null==data){
                        it.onNext(clazz.newInstance())
                    }else{
                        it.onNext(data)
                    }
                }
                else->it.onError(Error(errorMsg+"("+status+")"))
            }
        } }
    }

    fun <T> judgeListData(): Function<ResponseModel<T>, ObservableSource<T>>{
        return Function { (status, errorMsg, data) -> Observable.create {
            when(status){
                //登录失败
                100->{
//                    SPUtil.getInstance()?.clearUserInfo()
//                    EventBus.getDefault().post(EventBusBean(CodeUtil.LOGOUT_SUCCESS))
//                    val intent = Intent(MyApp.context, LoginActivity::class.java)
//                    intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
//                    MyApp.context?.startActivity(intent)
//                    it.onError(Error(errorMessage+"(603)"))
                }
                1->{
                    it.onNext(data)
                }
                else->it.onError(Error(errorMsg+"("+status+")"))
            }
        } }
    }
}