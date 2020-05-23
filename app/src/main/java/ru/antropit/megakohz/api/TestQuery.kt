package ru.antropit.megakohz.api

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.antropit.megakohz.api.model.Details
import ru.antropit.megakohz.api.model.Entity

interface TestQuery {
    @GET("test.php")
    fun loadTestQuery(): Flowable<List<Entity>>

    @GET("test.php")
    fun loadDetails(@Query("id") id: Int): Flowable<List<Details>>
}

