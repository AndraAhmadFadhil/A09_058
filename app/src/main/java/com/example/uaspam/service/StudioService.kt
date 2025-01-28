package com.example.uaspam.service

import com.example.uaspam.model.Studio
import com.example.uaspam.model.StudioResponse
import com.example.uaspam.model.StudioResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudioService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("api/studio")
    suspend fun getStudio(): StudioResponse

    @POST("api/studio/store")
    suspend fun insertStudio(@Body studio: Studio)

    @GET("api/studio/{id_studio}")
    suspend fun getStudioById(@Path("id_studio") id_studio: Int): StudioResponseDetail

    @PUT("api/studio/{id_studio}")
    suspend fun updateStudio(@Path("id_studio") id_studio: Int, @Body studio: Studio)

    @DELETE("api/studio/{id_studio}")
    suspend fun deleteStudio(@Path("id_studio")id_studio: Int): Response<Void>
}