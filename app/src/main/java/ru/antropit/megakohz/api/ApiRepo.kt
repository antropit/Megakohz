package ru.antropit.megakohz.api

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepo {
    const val API_URL = "http://megakohz.bget.ru/test_task/"

    fun createAdapter(): TestQuery {
        val adapter = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        return adapter.create<TestQuery>(TestQuery::class.java)
    }
}