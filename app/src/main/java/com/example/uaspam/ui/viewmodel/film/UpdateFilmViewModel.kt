package com.example.uaspam.ui.viewmodel.film

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.repo.FilmRepository
import com.example.uaspam.ui.navigation.DestinasiUpdateFilm
import kotlinx.coroutines.launch

class UpdateFilmViewModel(
    savedStateHandle: SavedStateHandle,
    private val filmRepository: FilmRepository
): ViewModel() {
    var filmUpdateUiState by mutableStateOf(InsertFilmUiState())
        private set

    private val _id_film: Int = checkNotNull(savedStateHandle[DestinasiUpdateFilm.ID_FILM])

    init {
        viewModelScope.launch {
            filmUpdateUiState = filmRepository.getFilmById(_id_film).data
                .toUiStateFilm()
        }
    }

    fun updateInsertFilmState(insertFilmUiEvent: InsertFilmUiEvent) {
        filmUpdateUiState = InsertFilmUiState(insertFilmUiEvent = insertFilmUiEvent)
    }

    suspend fun updateFilm() {
        viewModelScope.launch {
            try {
                println("Updating film with ID: $_id_film")
                filmRepository.updateFilm(
                    _id_film,
                    filmUpdateUiState.insertFilmUiEvent.toFilm()
                )
                println("Film updated successfully!")
            } catch (e: Exception) {
                println("Error updating film: ${e.message}")
            }
        }
    }

}