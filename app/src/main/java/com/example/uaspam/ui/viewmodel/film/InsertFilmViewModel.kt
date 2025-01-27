package com.example.uaspam.ui.viewmodel.film

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Film
import com.example.uaspam.repo.FilmRepository
import kotlinx.coroutines.launch

class InsertFilmViewModel(
    private val film: FilmRepository

): ViewModel(){

    var filmUiState by mutableStateOf(InsertFilmUiState())
        private set

    fun updateInsertFilmState(insertFilmUiEvent: InsertFilmUiEvent){
        filmUiState = InsertFilmUiState(insertFilmUiEvent = insertFilmUiEvent)
    }

    suspend fun insertFilm(){
        viewModelScope.launch {
            try {
                film.insertFilm(filmUiState.insertFilmUiEvent.toFilm())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

fun InsertFilmUiEvent.toFilm(): Film = Film(
    id_film = id_film ?: 0,
    judul_film = judul_film,
    durasi = durasi,
    deskripsi = deskripsi,
    genre = genre,
    rating_usia = rating_usia
)

fun Film.toInsertFilmUiEvent(): InsertFilmUiEvent = InsertFilmUiEvent(
    id_film = id_film ?: 0,
    judul_film = judul_film,
    durasi = durasi,
    deskripsi = deskripsi,
    genre = genre,
    rating_usia = rating_usia

)

fun Film.toUiStateFilm(): InsertFilmUiState = InsertFilmUiState(
    insertFilmUiEvent = toInsertFilmUiEvent()
)

data class InsertFilmUiState(
    val insertFilmUiEvent: InsertFilmUiEvent = InsertFilmUiEvent()
)

data class InsertFilmUiEvent(
    val id_film: Int? = null,
    val judul_film: String = "",
    val durasi: Int = 0,
    val deskripsi: String = "",
    val genre: String = "",
    val rating_usia: String = ""
)