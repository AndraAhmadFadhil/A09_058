package com.example.uaspam.ui.viewmodel.penayangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Film
import com.example.uaspam.model.FilmResponseDetail
import com.example.uaspam.model.PenayanganResponseDetail
import com.example.uaspam.model.Studio
import com.example.uaspam.repo.FilmRepository
import com.example.uaspam.repo.PenayanganRepository
import com.example.uaspam.repo.StudioRepository
import com.example.uaspam.ui.navigation.DestinasiDetailFilm
import com.example.uaspam.ui.navigation.DestinasiDetailPenayangan
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailPenayanganUiState {
    data class Success(val penayangan: PenayanganResponseDetail) : DetailPenayanganUiState()
    object Error : DetailPenayanganUiState()
    object Loading : DetailPenayanganUiState()
}

class DetailPenayanganViewModel(
    savedStateHandle: SavedStateHandle,
    private val penayangan: PenayanganRepository,
    private val id_film: FilmRepository,
    private val id_studio: StudioRepository
) : ViewModel() {

    var detailPenayanganUiState: DetailPenayanganUiState by mutableStateOf(DetailPenayanganUiState.Loading)
        private set

    private val _id_penayangan: Int = checkNotNull(savedStateHandle[DestinasiDetailPenayangan.ID_PENAYANGAN])

    init {
        getPenayanganById()
    }
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

    fun getPenayanganById() {
        viewModelScope.launch {
            detailPenayanganUiState = DetailPenayanganUiState.Loading
            detailPenayanganUiState = try {
                val penayangan = penayangan.getPenayanganById(_id_penayangan)
                DetailPenayanganUiState.Success(penayangan)
            } catch (e: IOException) {
                DetailPenayanganUiState.Error
            } catch (e: HttpException) {
                DetailPenayanganUiState.Error
            }
        }
    }
}