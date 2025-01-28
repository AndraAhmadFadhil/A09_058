package com.example.uaspam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Penayangan
import com.example.uaspam.model.TiketResponseDetail
import com.example.uaspam.repo.PenayanganRepository
import com.example.uaspam.repo.TiketRepository
import com.example.uaspam.ui.navigation.DestinasiDetailTiket
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailTiketUiState {
    data class Success(val tiket: TiketResponseDetail) : DetailTiketUiState()
    object Error : DetailTiketUiState()
    object Loading : DetailTiketUiState()
}

class DetailTiketViewModel(
    savedStateHandle: SavedStateHandle,
    private val tiket: TiketRepository,
    private val id_penayangan: PenayanganRepository
) : ViewModel() {

    var detailTiketUiState: DetailTiketUiState by mutableStateOf(DetailTiketUiState.Loading)
        private set

    private val _id_tiket: Int = checkNotNull(savedStateHandle[DestinasiDetailTiket.ID_TIKET])

    init {
        getTiketById()
    }

    var penayanganList by mutableStateOf<List<Penayangan>>(emptyList())

    init {
        loadPenayangan()
    }

    private fun loadPenayangan() {
        viewModelScope.launch {
            try {
                penayanganList = id_penayangan.getPenayangan().data
            } catch (e: Exception) {

            }
        }
    }

    fun getTiketById() {
        viewModelScope.launch {
            detailTiketUiState = DetailTiketUiState.Loading
            detailTiketUiState = try {
                val tiket = tiket.getTiketById(_id_tiket)
                DetailTiketUiState.Success(tiket)
            } catch (e: IOException) {
                DetailTiketUiState.Error
            } catch (e: HttpException) {
                DetailTiketUiState.Error
            }
        }
    }
}