package com.example.uaspam.service

import com.example.uaspam.model.Penayangan
import com.example.uaspam.model.PenayanganResponse
import com.example.uaspam.model.PenayanganResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PenayanganService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("api/penayangan")
    suspend fun getPenayangan(): PenayanganResponse

    @POST("api/penayangan/store")
    suspend fun insertPenayangan(@Body penayangan: Penayangan)

    @GET("api/penayangan/{id_penayangan}")
    suspend fun getPenayanganById(@Path("id_penayangan") id_penayangan: Int): PenayanganResponseDetail

    @PUT("api/penayangan/{id_penayangan}")
    suspend fun updatePenayangan(@Path("id_penayangan") id_penayangan: Int, @Body penayangan: Penayangan)

    @DELETE("api/penayangan/{id_penayangan}")
    suspend fun deletePenayangan(@Path("id_penayangan")id_penayangan: Int): Response<Void>
}