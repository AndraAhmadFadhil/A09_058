package com.example.uaspam.ui.viewmodel.studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Studio
import com.example.uaspam.repo.StudioRepository
import com.example.uaspam.ui.navigation.DestinasiUpdateStudio
import kotlinx.coroutines.launch

class UpdateStudioViewModel(
    savedStateHandle: SavedStateHandle,
    private val studioRepository: StudioRepository
): ViewModel() {

    var studioUpdateUiState by mutableStateOf(InsertStudioUiState())
        private set

    private val _id_studio: Int = checkNotNull(savedStateHandle[DestinasiUpdateStudio.ID_STUDIO])

    init {
        viewModelScope.launch {
            studioUpdateUiState = studioRepository.getStudioById(_id_studio).data
                .toUiStateStudio()
        }
    }

    fun updateInsertStudioState(insertStudioUiEvent: InsertStudioUiEvent) {
        studioUpdateUiState = InsertStudioUiState(insertStudioUiEvent = insertStudioUiEvent)
    }

    suspend fun updateStudio() {
        viewModelScope.launch {
            try {
                println("Updating studio with ID: $_id_studio")
                studioRepository.updateStudio(
                    _id_studio,
                    studioUpdateUiState.insertStudioUiEvent.toStudio()
                )
                println("Studio updated successfully!")
            } catch (e: Exception) {
                println("Error updating studio: ${e.message}")
            }
        }
    }

}