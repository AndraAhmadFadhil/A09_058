package com.example.uaspam.ui.view.penayangan

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.model.Film
import com.example.uaspam.model.Studio
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.customwidget.Dropdown
import com.example.uaspam.ui.navigation.DestinasiInsertFilm
import com.example.uaspam.ui.navigation.DestinasiInsertPenayangan
import com.example.uaspam.ui.viewmodel.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.film.InsertFilmUiEvent
import com.example.uaspam.ui.viewmodel.film.InsertFilmUiState
import com.example.uaspam.ui.viewmodel.film.InsertFilmViewModel
import com.example.uaspam.ui.viewmodel.penayangan.InsertPenayanganUiEvent
import com.example.uaspam.ui.viewmodel.penayangan.InsertPenayanganUiState
import com.example.uaspam.ui.viewmodel.penayangan.InsertPenayanganViewModel
import kotlinx.coroutines.launch


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPenayanganView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPenayanganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertPenayangan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) {innerPadding ->
        EntryBody(
            insertPenayanganUiState = viewModel.penayanganUiState,
            onPenayanganValueChange = viewModel::updateInsertPenayanganState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPenayangan()
                    navigateBack()
                }
            },
            filmList = viewModel.filmList,
            studioList = viewModel.studioList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )

    }
}

@Composable
fun EntryBody(
    insertPenayanganUiState: InsertPenayanganUiState,
    onPenayanganValueChange: (InsertPenayanganUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    filmList: List<Film>,
    studioList: List<Studio>
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertPenayanganUiEvent = insertPenayanganUiState.insertPenayanganUiEvent,
            onValueChange = onPenayanganValueChange,
            modifier = Modifier.fillMaxWidth(),
            filmList = filmList,
            studioList = studioList,
            insertPenayanganUiState = insertPenayanganUiState
        )

        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertPenayanganUiEvent: InsertPenayanganUiEvent,
    insertPenayanganUiState: InsertPenayanganUiState,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPenayanganUiEvent) -> Unit = {},
    enabled: Boolean = true,
    filmList: List<Film>,
    studioList: List<Studio>
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Dropdown(
            selectedValue = filmList.find {it.id_film == insertPenayanganUiState.insertPenayanganUiEvent.id_film}?.judul_film ?: "",
            options = filmList.map {it.judul_film},
            label = "Pilih Film",
            onValueChangedEvent = {judul ->
                val pilihFilm = filmList.firstOrNull { it.judul_film == judul}
                pilihFilm?.let {
                    onValueChange(insertPenayanganUiState.insertPenayanganUiEvent.copy(id_film = it.id_film))
                }
            }
        )
        Dropdown(
            selectedValue = studioList.find {it.id_studio == insertPenayanganUiState.insertPenayanganUiEvent.id_studio}?.nama_studio ?: "",
            options = studioList.map {it.nama_studio},
            label = "Pilih Studio",
            onValueChangedEvent = {judul ->
                val pilihStudio = studioList.firstOrNull { it.nama_studio == judul}
                pilihStudio?.let {
                    onValueChange(insertPenayanganUiState.insertPenayanganUiEvent.copy(id_studio = it.id_studio))
                }
            }
        )
        OutlinedTextField(
            value = insertPenayanganUiEvent.tanggal_penayangan,
            onValueChange = {onValueChange(insertPenayanganUiEvent.copy(tanggal_penayangan = it))},
            label = { Text("Tanggal Penayangan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPenayanganUiEvent.harga_tiket,
            onValueChange = {onValueChange(insertPenayanganUiEvent.copy(harga_tiket = it))},
            label = { Text("Harga TIket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}