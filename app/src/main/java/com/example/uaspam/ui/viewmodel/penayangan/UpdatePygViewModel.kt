package com.example.uaspam.ui.viewmodel.penayangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Film
import com.example.uaspam.model.Studio
import com.example.uaspam.repo.FilmRepository
import com.example.uaspam.repo.PenayanganRepository
import com.example.uaspam.repo.StudioRepository
import com.example.uaspam.ui.navigation.DestinasiUpdateFilm
import com.example.uaspam.ui.navigation.DestinasiUpdatePenayangan
import com.example.uaspam.ui.viewmodel.film.InsertFilmUiEvent
import com.example.uaspam.ui.viewmodel.film.InsertFilmUiState
import com.example.uaspam.ui.viewmodel.film.toFilm
import com.example.uaspam.ui.viewmodel.film.toUiStateFilm
import kotlinx.coroutines.launch

class UpdatePenayanganViewModel(
    savedStateHandle: SavedStateHandle,
    private val penayanganRepository: PenayanganRepository,
    private val id_film: FilmRepository,
    private val id_studio: StudioRepository
): ViewModel() {
    var penayanganUpdateUiState by mutableStateOf(InsertPenayanganUiState())
        private set
    var filmList by mutableStateOf<List<Film>>(emptyList())
    var studioList by mutableStateOf<List<Studio>>(emptyList())

    init {
        loadFilmDanStudio()
    }

    private fun loadFilmDanStudio() {
        viewModelScope.launch {
            try {
                filmList = id_film.getFilm().data
                studioList = id_studio.getStudio().data
            } catch (e: Exception) {

            }
        }
    }

    private val _id_penayangan: Int = checkNotNull(savedStateHandle[DestinasiUpdatePenayangan.ID_PENAYANGAN])

    init {
        viewModelScope.launch {
            penayanganUpdateUiState = penayanganRepository.getPenayanganById(_id_penayangan).data
                .toUiStatePenayangan()
        }
    }

    fun updateInsertPenayanganState(insertPenayanganUiEvent: InsertPenayanganUiEvent) {
        penayanganUpdateUiState = InsertPenayanganUiState(insertPenayanganUiEvent = insertPenayanganUiEvent)
    }

    suspend fun updatePenayangan() {
        viewModelScope.launch {
            try {
                println("Updating penayangan with ID: $_id_penayangan")
                penayanganRepository.updatePenayangan(
                    _id_penayangan,
                    penayanganUpdateUiState.insertPenayanganUiEvent.toPenayangan()
                )
                println("Penayangan updated successfully!")
            } catch (e: Exception) {
                println("Error updating penayangan: ${e.message}")
            }
        }
    }

}