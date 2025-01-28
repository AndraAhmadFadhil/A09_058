package com.example.uaspam.ui.viewmodel.penayangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam.model.Penayangan
import com.example.uaspam.repo.PenayanganRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePenayanganUiState {
    data class Success(val penayangan: List<Penayangan>) : HomePenayanganUiState()
    object Error : HomePenayanganUiState()
    object Loading : HomePenayanganUiState()
}

class HomePenayanganViewModel(private val penayangan: PenayanganRepository) : ViewModel(){
    var penayanganUIState: HomePenayanganUiState by mutableStateOf(HomePenayanganUiState.Loading)
        private set

    init {
        getPenayangan()
    }

    fun getPenayangan(){
        viewModelScope.launch {
            penayanganUIState = HomePenayanganUiState.Loading
            penayanganUIState = try {
                HomePenayanganUiState.Success(penayangan.getPenayangan().data)
            } catch (e: IOException) {
                HomePenayanganUiState.Error
            } catch (e: HttpException){
                HomePenayanganUiState.Error
            }
        }
    }

    fun deletePenayangan(id_penayangan: Int) {
        viewModelScope.launch {
            try {
                penayangan.deletePenayangan(id_penayangan)
            } catch (e: IOException) {
                HomePenayanganUiState.Error
            } catch (e: HttpException) {
                HomePenayanganUiState.Error
            }
        }
    }
}