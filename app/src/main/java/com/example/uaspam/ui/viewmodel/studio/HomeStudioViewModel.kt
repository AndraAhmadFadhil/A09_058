package com.example.uaspam.ui.viewmodel.studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam.model.Studio
import com.example.uaspam.repo.StudioRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeStudioUiState {
    data class Success(val studio: List<Studio>) : HomeStudioUiState()
    object Error : HomeStudioUiState()
    object Loading : HomeStudioUiState()
}

class HomeStudioViewModel(private val studio: StudioRepository) : ViewModel(){
    var studioUIState: HomeStudioUiState by mutableStateOf(HomeStudioUiState.Loading)
        private set

    init {
        getStudio()
    }

    fun getStudio(){
        viewModelScope.launch {
            studioUIState = HomeStudioUiState.Loading
            studioUIState = try {
                HomeStudioUiState.Success(studio.getStudio().data)
            } catch (e: IOException) {
                HomeStudioUiState.Error
            } catch (e: HttpException){
                HomeStudioUiState.Error
            }
        }
    }

    fun deleteStudio(id_studio: Int) {
        viewModelScope.launch {
            try {
                studio.deleteStudio(id_studio)
            } catch (e: IOException) {
                HomeStudioUiState.Error
            } catch (e: HttpException) {
                HomeStudioUiState.Error
            }
        }
    }
}