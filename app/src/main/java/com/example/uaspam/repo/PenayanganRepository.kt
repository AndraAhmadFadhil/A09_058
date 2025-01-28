package com.example.uaspam.repo

import com.example.uaspam.model.Film
import com.example.uaspam.model.FilmResponse
import com.example.uaspam.model.FilmResponseDetail
import com.example.uaspam.model.Penayangan
import com.example.uaspam.model.PenayanganResponse
import com.example.uaspam.model.PenayanganResponseDetail
import com.example.uaspam.service.FilmService
import com.example.uaspam.service.PenayanganService
import java.io.IOException

interface PenayanganRepository{

    suspend fun getPenayangan(): PenayanganResponse
    suspend fun insertPenayangan(penayangan: Penayangan)
    suspend fun updatePenayangan(id_penayangan: Int, penayangan: Penayangan)
    suspend fun deletePenayangan(id_penayangan: Int)
    suspend fun getPenayanganById(id_penayangan: Int): PenayanganResponseDetail
}

class NetworkPenayanganRepository(

    private val PenayanganApiService: PenayanganService
): PenayanganRepository{

    override suspend fun getPenayangan(): PenayanganResponse {
        return PenayanganApiService.getPenayangan()
    }

    override suspend fun insertPenayangan(penayangan: Penayangan) {
        PenayanganApiService.insertPenayangan(penayangan)
    }

    override suspend fun updatePenayangan(id_penayangan: Int, penayangan: Penayangan) {
        PenayanganApiService.updatePenayangan(id_penayangan, penayangan)
    }

    override suspend fun deletePenayangan(id_penayangan: Int) {
        try {
            val response = PenayanganApiService.deletePenayangan(id_penayangan)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Penayangan. HTTP Status code:" +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun getPenayanganById(id_penayangan: Int): PenayanganResponseDetail {
        return PenayanganApiService.getPenayanganById(id_penayangan)
    }
}