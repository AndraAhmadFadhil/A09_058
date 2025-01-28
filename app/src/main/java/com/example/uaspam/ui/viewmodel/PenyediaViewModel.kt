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
import com.example.uaspam.ui.viewmodel.penayangan.DetailPenayanganViewModel
import com.example.uaspam.ui.viewmodel.penayangan.HomePenayanganViewModel
import com.example.uaspam.ui.viewmodel.penayangan.InsertPenayanganViewModel
import com.example.uaspam.ui.viewmodel.penayangan.UpdatePenayanganViewModel
import com.example.uaspam.ui.viewmodel.studio.DetailStudioViewModel
import com.example.uaspam.ui.viewmodel.studio.HomeStudioViewModel
import com.example.uaspam.ui.viewmodel.studio.InsertStudioViewModel
import com.example.uaspam.ui.viewmodel.studio.UpdateStudioViewModel
import com.example.uaspam.ui.viewmodel.tiket.DetailTiketViewModel
import com.example.uaspam.ui.viewmodel.tiket.HomeTiketViewModel
import com.example.uaspam.ui.viewmodel.tiket.InsertTiketViewModel
import com.example.uaspam.ui.viewmodel.tiket.UpdateTiketViewModel

object PenyediaViewModel{
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    val Factory = viewModelFactory {
        initializer { HomeFilmViewModel(aplikasiFilm().container.FilmRepository) }
        initializer { InsertFilmViewModel(aplikasiFilm().container.FilmRepository) }
        initializer { DetailFilmViewModel(createSavedStateHandle(),aplikasiFilm().container.FilmRepository) }
        initializer { UpdateFilmViewModel(createSavedStateHandle(),aplikasiFilm().container.FilmRepository) }
        initializer { HomeStudioViewModel(aplikasiFilm().container.StudioRepository) }
        initializer { InsertStudioViewModel(aplikasiFilm().container.StudioRepository) }
        initializer { DetailStudioViewModel(createSavedStateHandle(),aplikasiFilm().container.StudioRepository) }
        initializer { UpdateStudioViewModel(createSavedStateHandle(),aplikasiFilm().container.StudioRepository) }
        initializer { HomePenayanganViewModel(aplikasiFilm().container.PenayanganRepository) }
        initializer { InsertPenayanganViewModel(aplikasiFilm().container.PenayanganRepository,aplikasiFilm().container.FilmRepository, aplikasiFilm().container.StudioRepository) }
        initializer { UpdatePenayanganViewModel(createSavedStateHandle(),aplikasiFilm().container.PenayanganRepository,aplikasiFilm().container.FilmRepository, aplikasiFilm().container.StudioRepository) }
        initializer { DetailPenayanganViewModel(createSavedStateHandle(),aplikasiFilm().container.PenayanganRepository,aplikasiFilm().container.FilmRepository, aplikasiFilm().container.StudioRepository) }
        initializer { HomeTiketViewModel(aplikasiFilm().container.TiketRepository) }
        initializer { InsertTiketViewModel(aplikasiFilm().container.TiketRepository,aplikasiFilm().container.PenayanganRepository) }
        initializer { UpdateTiketViewModel(createSavedStateHandle(),aplikasiFilm().container.TiketRepository,aplikasiFilm().container.PenayanganRepository) }
        initializer { DetailTiketViewModel(createSavedStateHandle(),aplikasiFilm().container.TiketRepository,aplikasiFilm().container.PenayanganRepository) }


    }
}

fun CreationExtras.aplikasiFilm(): FilmApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FilmApplication)