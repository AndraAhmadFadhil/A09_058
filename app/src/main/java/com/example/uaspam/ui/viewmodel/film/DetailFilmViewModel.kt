package com.example.uaspam.ui.viewmodel.film

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.FilmResponseDetail
import com.example.uaspam.repo.FilmRepository
import com.example.uaspam.ui.navigation.DestinasiDetailFilm
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailFilmUiState {
    data class Success(val film: FilmResponseDetail) : DetailFilmUiState()
    object Error : DetailFilmUiState()
    object Loading : DetailFilmUiState()
}

class DetailFilmViewModel(
    savedStateHandle: SavedStateHandle,
    private val film: FilmRepository
) : ViewModel() {

    var detailFilmUiState: DetailFilmUiState by mutableStateOf(DetailFilmUiState.Loading)
        private set

    private val _id_film: Int = checkNotNull(savedStateHandle[DestinasiDetailFilm.ID_FILM])

    init {
        getFilmById()
    }

    fun getFilmById() {
        viewModelScope.launch {
            detailFilmUiState = DetailFilmUiState.Loading
            detailFilmUiState = try {
                val film = film.getFilmById(_id_film)
                DetailFilmUiState.Success(film)
            } catch (e: IOException) {
                DetailFilmUiState.Error
            } catch (e: HttpException) {
                DetailFilmUiState.Error
            }
        }
    }
}