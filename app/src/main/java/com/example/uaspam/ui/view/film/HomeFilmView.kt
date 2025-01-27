package com.example.uaspam.ui.view.film

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.model.Film
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiHomeFilm
import com.example.uaspam.ui.navigation.DestinasiInsertFilm
import com.example.uaspam.ui.navigation.DestinasiNavigasi
import com.example.uaspam.ui.viewmodel.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.film.HomeFilmUiState
import com.example.uaspam.ui.viewmodel.film.HomeFilmViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmScreen(
    navigateToFilmEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    navigateBack: () -> Unit,
    @SuppressLint("NewApi") viewModel: HomeFilmViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeFilm.titleRes,
                navigateUp = navigateBack,
                canNavigateBack = true,
                )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToFilmEntry) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Add Film")
            }
        },
    ) { innerPadding ->
        FilmStatus(
            filmUiState = viewModel.filmUIState,
            retryAction = { viewModel.getFilm() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteFilm(it.id_film ?: 0)
                viewModel.getFilm()
            }
        )
    }
}

@Composable
fun FilmStatus(
    filmUiState: HomeFilmUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Film) -> Unit
) {
    when (filmUiState) {
        is HomeFilmUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeFilmUiState.Success ->
            if (filmUiState.film.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Film")
                }
            } else {
                FilmLayout(
                    films = filmUiState.film,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick (it.id_film ?: 0) },
                    onDeleteClick = onDeleteClick
                )
            }
        is HomeFilmUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Terjadi kesalahan", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text("Coba Lagi")
        }
    }
}

@Composable
fun FilmLayout(
    films: List<Film>,
    modifier: Modifier = Modifier,
    onDetailClick: (Film) -> Unit,
    onDeleteClick: (Film) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(films) { film ->
            FilmCard(
                film = film,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(film) },
                onDeleteClick = { onDeleteClick(film) }
            )
        }
    }
}


@Composable
fun FilmCard(
    film: Film,
    modifier: Modifier = Modifier,
    onDeleteClick: (Film) -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Judul: ${film.judul_film}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Durasi: ${film.durasi} menit", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Deskripsi: ${film.deskripsi}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Genre: ${film.genre}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Rating Usia: ${film.rating_usia}", style = MaterialTheme.typography.bodyMedium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                IconButton(onClick = { onDeleteClick(film) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Film")
                }
            }
        }
    }
}
