package com.example.uaspam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Penayangan
import com.example.uaspam.model.Tiket
import com.example.uaspam.repo.PenayanganRepository
import com.example.uaspam.repo.TiketRepository
import com.example.uaspam.ui.navigation.DestinasiUpdateTiket
import kotlinx.coroutines.launch

class UpdateTiketViewModel(
    savedStateHandle: SavedStateHandle,
    private val tiketRepository: TiketRepository,
    private val id_penayangan: PenayanganRepository,
): ViewModel() {
    var tiketUpdateUiState by mutableStateOf(InsertTiketUiState())
        private set

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

    private val _id_tiket: Int = checkNotNull(savedStateHandle[DestinasiUpdateTiket.ID_TIKET])

    init {
        viewModelScope.launch {
            tiketUpdateUiState = tiketRepository.getTiketById(_id_tiket).data
                .toUiStateTiket()
        }
    }

    fun updateInsertTiketState(insertTiketUiEvent: InsertTiketUiEvent) {
        tiketUpdateUiState = InsertTiketUiState(insertTiketUiEvent = insertTiketUiEvent)
    }

    suspend fun updateTiket() {
        viewModelScope.launch {
            try {
                println("Updating tiket with ID: $_id_tiket")
                tiketRepository.updateTiket(
                    _id_tiket,
                    tiketUpdateUiState.insertTiketUiEvent.toTiket()
                )
                println("Tiket updated successfully!")
            } catch (e: Exception) {
                println("Error updating tiket: ${e.message}")
            }
        }
    }

}