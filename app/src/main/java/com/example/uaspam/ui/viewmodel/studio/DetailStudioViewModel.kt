package com.example.uaspam.ui.viewmodel.studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.StudioResponseDetail
import com.example.uaspam.repo.StudioRepository
import com.example.uaspam.ui.navigation.DestinasiDetailStudio
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailStudioUiState {
    data class Success(val studio: StudioResponseDetail) : DetailStudioUiState()
    object Error : DetailStudioUiState()
    object Loading : DetailStudioUiState()
}

class DetailStudioViewModel(
    savedStateHandle: SavedStateHandle,
    private val studio: StudioRepository
) : ViewModel() {

    var detailStudioUiState: DetailStudioUiState by mutableStateOf(DetailStudioUiState.Loading)
        private set

    private val _id_studio: Int = checkNotNull(savedStateHandle[DestinasiDetailStudio.ID_STUDIO])

    init {
        getStudioById()
    }

    fun getStudioById() {
        viewModelScope.launch {
            detailStudioUiState = DetailStudioUiState.Loading
            detailStudioUiState = try {
                val studio = studio.getStudioById(_id_studio)
                DetailStudioUiState.Success(studio)
            } catch (e: IOException) {
                DetailStudioUiState.Error
            } catch (e: HttpException) {
                DetailStudioUiState.Error
            }
        }
    }
}