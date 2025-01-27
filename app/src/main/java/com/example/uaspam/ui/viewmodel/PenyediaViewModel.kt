package com.example.uaspam.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uaspam.FilmApplication
import com.example.uaspam.ui.viewmodel.film.DetailFilmViewModel
import com.example.uaspam.ui.viewmodel.film.HomeFilmViewModel
import com.example.uaspam.ui.viewmodel.film.InsertFilmViewModel
import com.example.uaspam.ui.viewmodel.film.UpdateFilmViewModel
import com.example.uaspam.ui.viewmodel.penayangan.HomePenayanganViewModel
import com.example.uaspam.ui.viewmodel.penayangan.InsertPenayanganViewModel
import com.example.uaspam.ui.viewmodel.studio.DetailStudioViewModel
import com.example.uaspam.ui.viewmodel.studio.HomeStudioViewModel
import com.example.uaspam.ui.viewmodel.studio.InsertStudioViewModel
import com.example.uaspam.ui.viewmodel.studio.UpdateStudioViewModel

object PenyediaViewModel{
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    val Factory = viewModelFactory {
        initializer { HomeFilmViewModel(aplikasiFilm().container.FilmRepository) }
        initializer { InsertFilmViewModel(aplikasiFilm().container.FilmRepository) }
        initializer { DetailFilmViewModel(createSavedStateHandle(),aplikasiFilm().container.FilmRepository) }
        initializer { UpdateFilmViewModel(createSavedStateHandle(),aplikasiFilm().container.FilmRepository) }
    }
}

fun CreationExtras.aplikasiFilm(): FilmApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FilmApplication)