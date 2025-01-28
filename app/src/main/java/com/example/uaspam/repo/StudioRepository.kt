package com.example.uaspam.repo

import com.example.uaspam.model.Studio
import com.example.uaspam.model.StudioResponse
import com.example.uaspam.model.StudioResponseDetail
import com.example.uaspam.service.StudioService
import java.io.IOException

interface StudioRepository{

    suspend fun getStudio(): StudioResponse
    suspend fun insertStudio(studio: Studio)
    suspend fun updateStudio(id_studio: Int, studio: Studio)
    suspend fun deleteStudio(id_studio: Int)
    suspend fun getStudioById(id_studio: Int): StudioResponseDetail
}

class NetworkStudioRepository(

    private val StudioApiService: StudioService
): StudioRepository{
    override suspend fun getStudio(): StudioResponse {
        return StudioApiService.getStudio()
    }

    override suspend fun insertStudio(studio: Studio) {
        StudioApiService.insertStudio(studio)
    }

    override suspend fun updateStudio(id_studio: Int, studio: Studio) {
        StudioApiService.updateStudio(id_studio, studio)
    }

    override suspend fun deleteStudio(id_studio: Int) {
        try {
            val response = StudioApiService.deleteStudio(id_studio)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Studio. HTTP Status code:" +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun getStudioById(id_studio: Int): StudioResponseDetail {
        return StudioApiService.getStudioById(id_studio)
    }

}