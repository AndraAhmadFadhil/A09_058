package com.example.uaspam.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.uaspam.ui.navigation.DestinasiNavigasi
import com.example.uaspam.ui.viewmodel.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.film.HomeFilmUiState
import com.example.uaspam.ui.viewmodel.film.HomeFilmViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToFilmList: () -> Unit,
    navigateToFilmEntry: () -> Unit,
    navigateToInsertFilm: () -> Unit,
    navigateToHomeStudio: () -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: (Film) -> Unit = {},
    onCardClick: (Film) -> Unit = {},
    navigateToPenayangan: () -> Unit,
    @SuppressLint("NewApi") viewModel: HomeFilmViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Home") })
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            FilmStatus(
                filmUiState = viewModel.filmUIState,
                retryAction = { viewModel.getFilm() },
                modifier = Modifier.fillMaxWidth(),
                onEditClick = onEditClick,
                onDeleteClick = {
                    viewModel.deleteFilm(it.id_film ?: 0)
                },
                onCardClick = onCardClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = navigateToInsertFilm,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Film")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Tambah Film")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = navigateToPenayangan,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp)
            ) {
                Text(text = "Lihat Penayangan")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = navigateToHomeStudio,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp)
            ) {
                Text(text = "Halaman Studio")
            }
        }
    }
}

@Composable
fun FilmStatus(
    filmUiState: HomeFilmUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: (Film) -> Unit,
    onDeleteClick: (Film) -> Unit,
    onCardClick: (Film) -> Unit
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
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick,
                    onCardClick = onCardClick
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
    onEditClick: (Film) -> Unit,
    onDeleteClick: (Film) -> Unit,
    onCardClick: (Film) -> Unit
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
                    .clickable { onEditClick(film) }
                    .clickable {onCardClick(film)},
                onEditClick = onEditClick,
                onDeleteClick = { onDeleteClick(film) },

            )
        }
    }
}

@Composable
fun FilmCard(
    film: Film,
    modifier: Modifier = Modifier,
    onEditClick: (Film) -> Unit,
    onDeleteClick: (Film) -> Unit,

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


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

            }
        }
    }
}