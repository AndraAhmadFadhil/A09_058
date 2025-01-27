package com.example.uaspam.di

import com.example.uaspam.model.Penayangan
import com.example.uaspam.repo.FilmRepository
import com.example.uaspam.repo.NetworkFilmRepository
import com.example.uaspam.repo.NetworkPenayanganRepository
import com.example.uaspam.repo.NetworkStudioRepository
import com.example.uaspam.repo.PenayanganRepository
import com.example.uaspam.repo.StudioRepository
import com.example.uaspam.service.FilmService
import com.example.uaspam.service.PenayanganService
import com.example.uaspam.service.StudioService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val FilmRepository: FilmRepository
    val StudioRepository: StudioRepository
    val PenayanganRepository: PenayanganRepository
    //tambahin tabel lainnya juga ntar
}

class FilmContainer: AppContainer{

    private val baseUrl ="http://10.0.2.2:8000/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val filmService: FilmService by lazy {
        retrofit.create(FilmService::class.java)
    }

    override val FilmRepository: FilmRepository by lazy {
        NetworkFilmRepository(filmService)
    }

    private val studioService: StudioService by lazy {
        retrofit.create(StudioService::class.java)
    }

    override val StudioRepository: StudioRepository by lazy {
        NetworkStudioRepository(studioService)
    }

    private val penayanganService: PenayanganService by lazy {
        retrofit.create(PenayanganService::class.java)
    }

    override val PenayanganRepository: PenayanganRepository by lazy {
        NetworkPenayanganRepository(penayanganService)
    }




}