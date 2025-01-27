package com.example.uaspam.service

import com.example.uaspam.model.Film
import com.example.uaspam.model.FilmResponse
import com.example.uaspam.model.FilmResponseDetail
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FilmService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("api/film")
    suspend fun getFilm(): FilmResponse

    @POST("api/film/store")
    suspend fun insertFilm(@Body film: Film)

    @GET("api/film/{id_film}")
    suspend fun getFilmById(@Path("id_film") id_film: Int): FilmResponseDetail

    @PUT("api/film/{id_film}")
    suspend fun updateFilm(@Path("id_film") id_film: Int, @Body film: Film)

    @DELETE("api/film/{id_film}")
    suspend fun deleteFilm(@Path("id_film")id_film: Int): Response<Void>

}