package com.example.uaspam.service

import com.example.uaspam.model.Tiket
import com.example.uaspam.model.TiketResponse
import com.example.uaspam.model.TiketResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TiketService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("api/tiket")
    suspend fun getTiket(): TiketResponse

    @POST("api/tiket/store")
    suspend fun insertTiket(@Body tiket: Tiket)

    @GET("api/tiket/{id_tiket}")
    suspend fun getTiketById(@Path("id_tiket") id_tiket: Int): TiketResponseDetail

    @PUT("api/tiket/{id_tiket}")
    suspend fun updateTiket(@Path("id_tiket") id_tiket: Int, @Body tiket: Tiket)

    @DELETE("api/tiket/{id_tiket}")
    suspend fun deleteTiket(@Path("id_tiket")id_tiket: Int): Response<Void>
}