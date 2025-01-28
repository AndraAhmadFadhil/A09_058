package com.example.uaspam.ui.viewmodel.studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Studio
import com.example.uaspam.repo.StudioRepository
import kotlinx.coroutines.launch

class InsertStudioViewModel(
    private val studio: StudioRepository

): ViewModel(){

    var studioUiState by mutableStateOf(InsertStudioUiState())
        private set

    fun updateInsertStudioState(insertStudioUiEvent: InsertStudioUiEvent){
        studioUiState = InsertStudioUiState(insertStudioUiEvent = insertStudioUiEvent)
    }

    suspend fun insertStudio(){
        viewModelScope.launch {
            try {
                studio.insertStudio(studioUiState.insertStudioUiEvent.toStudio())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

fun InsertStudioUiEvent.toStudio(): Studio = Studio(
    id_studio = id_studio ?: 0,
    nama_studio = nama_studio,
    kapasitas = kapasitas,
)

fun Studio.toInsertStudioUiEvent(): InsertStudioUiEvent = InsertStudioUiEvent(
    id_studio = id_studio ?: 0,
    nama_studio = nama_studio,
    kapasitas = kapasitas,
)

fun Studio.toUiStateStudio(): InsertStudioUiState = InsertStudioUiState(
    insertStudioUiEvent = toInsertStudioUiEvent()
)

data class InsertStudioUiState(
    val insertStudioUiEvent: InsertStudioUiEvent = InsertStudioUiEvent()
)

data class InsertStudioUiEvent(
    val id_studio: Int? = null,
    val nama_studio: String = "",
    val kapasitas: Int = 0,
)