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

