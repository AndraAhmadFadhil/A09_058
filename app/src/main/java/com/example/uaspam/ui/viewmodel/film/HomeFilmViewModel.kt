package com.example.uaspam.ui.viewmodel.film

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam.model.Film
import com.example.uaspam.repo.FilmRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeFilmUiState {
    data class Success(val film: List<Film>) : HomeFilmUiState()
    object Error : HomeFilmUiState()
    object Loading : HomeFilmUiState()
}

class HomeFilmViewModel(private val film: FilmRepository) : ViewModel(){
    var filmUIState: HomeFilmUiState by mutableStateOf(HomeFilmUiState.Loading)
        private set

    init {
        getFilm()
    }

    fun getFilm(){
        viewModelScope.launch {
            filmUIState = HomeFilmUiState.Loading
            filmUIState = try {
                HomeFilmUiState.Success(film.getFilm().data)
            } catch (e: IOException) {
                HomeFilmUiState.Error
            } catch (e: HttpException){
                HomeFilmUiState.Error
            }
        }
    }

    fun deleteFilm(id_film: Int) {
        viewModelScope.launch {
            try {
                film.deleteFilm(id_film)
            } catch (e: IOException) {
                HomeFilmUiState.Error
            } catch (e: HttpException) {
                HomeFilmUiState.Error
            }
        }
    }
}