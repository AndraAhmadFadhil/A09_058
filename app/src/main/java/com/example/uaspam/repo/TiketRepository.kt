package com.example.uaspam.repo

import com.example.uaspam.model.Tiket
import com.example.uaspam.model.TiketResponse
import com.example.uaspam.model.TiketResponseDetail
import com.example.uaspam.service.TiketService
import java.io.IOException

interface TiketRepository{

    suspend fun getTiket(): TiketResponse
    suspend fun insertTiket(tiket: Tiket)
    suspend fun updateTiket(id_tiket: Int, tiket: Tiket)
    suspend fun deleteTiket(id_tiket: Int)
    suspend fun getTiketById(id_tiket: Int): TiketResponseDetail
}

class NetworkTiketRepository(
    private val TiketApiService: TiketService
): TiketRepository{

    override suspend fun getTiket(): TiketResponse {
        return TiketApiService.getTiket()
    }

    override suspend fun insertTiket(tiket: Tiket) {
        TiketApiService.insertTiket(tiket)
    }

    override suspend fun updateTiket(id_tiket: Int, tiket: Tiket) {
        TiketApiService.updateTiket(id_tiket, tiket)
    }

    override suspend fun deleteTiket(id_tiket: Int) {
        try {
            val response = TiketApiService.deleteTiket(id_tiket)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Tiket. HTTP Status code:" +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception){
            throw e
        }
    }


    override suspend fun getTiketById(id_tiket: Int): TiketResponseDetail {
        return TiketApiService.getTiketById(id_tiket)
    }
}