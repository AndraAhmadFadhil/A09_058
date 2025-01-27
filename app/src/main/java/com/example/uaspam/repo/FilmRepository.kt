package com.example.uaspam.repo

import com.example.uaspam.model.Film
import com.example.uaspam.model.FilmResponse
import com.example.uaspam.model.FilmResponseDetail
import com.example.uaspam.service.FilmService
import java.io.IOException

interface FilmRepository{

    suspend fun getFilm(): FilmResponse
    suspend fun insertFilm(film: Film)
    suspend fun updateFilm(id_film: Int, film: Film)
    suspend fun deleteFilm(id_film: Int)
    suspend fun getFilmById(id_film: Int): FilmResponseDetail
}

class NetworkFilmRepository(

    private val FilmApiService: FilmService
): FilmRepository{
    override suspend fun getFilm(): FilmResponse {
        return FilmApiService.getFilm()
    }

    override suspend fun insertFilm(film: Film) {
        FilmApiService.insertFilm(film)
    }

    override suspend fun updateFilm(id_film: Int, film: Film) {
        FilmApiService.updateFilm(id_film, film)
    }

    override suspend fun deleteFilm(id_film: Int) {
        try {
            val response = FilmApiService.deleteFilm(id_film)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Film. HTTP Status code:" +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun getFilmById(id_film: Int): FilmResponseDetail {
        return FilmApiService.getFilmById(id_film)
    }
}