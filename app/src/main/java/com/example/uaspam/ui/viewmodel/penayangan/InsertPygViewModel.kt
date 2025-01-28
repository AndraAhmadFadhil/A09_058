package com.example.uaspam.ui.viewmodel.penayangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Film
import com.example.uaspam.model.Penayangan
import com.example.uaspam.model.Studio
import com.example.uaspam.repo.FilmRepository
import com.example.uaspam.repo.PenayanganRepository
import com.example.uaspam.repo.StudioRepository
import kotlinx.coroutines.launch

class InsertPenayanganViewModel(
    private val penayangan: PenayanganRepository,
    private val id_film: FilmRepository,
    private val id_studio: StudioRepository

): ViewModel(){

    var penayanganUiState by mutableStateOf(InsertPenayanganUiState())
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

    fun updateInsertPenayanganState(insertPenayanganUiEvent: InsertPenayanganUiEvent){
        penayanganUiState = InsertPenayanganUiState(insertPenayanganUiEvent = insertPenayanganUiEvent)
    }

    suspend fun insertPenayangan(){
        viewModelScope.launch {
            try {
                penayangan.insertPenayangan(penayanganUiState.insertPenayanganUiEvent.toPenayangan())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

fun InsertPenayanganUiEvent.toPenayangan(): Penayangan = Penayangan(
    id_penayangan = id_penayangan ?: 0 ,
    id_film = id_film ?: 0,
    id_studio = id_studio ?: 0,
    tanggal_penayangan = tanggal_penayangan,
    harga_tiket = harga_tiket
)

fun Penayangan.toInsertPenayanganUiEvent(): InsertPenayanganUiEvent = InsertPenayanganUiEvent(
    id_penayangan = id_penayangan ?: 0 ,
    id_film = id_film ?: 0,
    id_studio = id_studio ?: 0,
    tanggal_penayangan = tanggal_penayangan,
    harga_tiket = harga_tiket
)

fun Penayangan.toUiStatePenayangan(): InsertPenayanganUiState = InsertPenayanganUiState(
    insertPenayanganUiEvent = toInsertPenayanganUiEvent()
)

data class InsertPenayanganUiState(
    val insertPenayanganUiEvent: InsertPenayanganUiEvent = InsertPenayanganUiEvent()
)

data class InsertPenayanganUiEvent(
    val id_penayangan: Int? = null,
    val id_film: Int? = null,
    val id_studio: Int? = null,
    val tanggal_penayangan: String = "",
    val harga_tiket: String = "",
)