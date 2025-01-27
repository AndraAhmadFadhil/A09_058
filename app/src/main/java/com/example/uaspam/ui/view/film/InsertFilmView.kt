package com.example.uaspam.ui.view.film

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiInsertFilm
import com.example.uaspam.ui.navigation.DestinasiNavigasi
import com.example.uaspam.ui.viewmodel.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.film.InsertFilmUiEvent
import com.example.uaspam.ui.viewmodel.film.InsertFilmUiState
import com.example.uaspam.ui.viewmodel.film.InsertFilmViewModel
import kotlinx.coroutines.launch


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertFilmView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertFilmViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertFilm.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) {innerPadding ->
        EntryBody(
            insertFilmUiState = viewModel.filmUiState,
            onFilmValueChange = viewModel::updateInsertFilmState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertFilm()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )

    }
}

@Composable
fun EntryBody(
    insertFilmUiState: InsertFilmUiState,
    onFilmValueChange: (InsertFilmUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertFilmUiEvent = insertFilmUiState.insertFilmUiEvent,
            onValueChange = onFilmValueChange,
            modifier = Modifier.fillMaxWidth())
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
    insertFilmUiEvent: InsertFilmUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertFilmUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertFilmUiEvent.judul_film,
            onValueChange = {onValueChange(insertFilmUiEvent.copy(judul_film = it))},
            label = { Text("Judul FIlm") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertFilmUiEvent.durasi.toString(),
            onValueChange = {onValueChange(insertFilmUiEvent.copy(durasi = it.toInt()))},
            label = { Text("Durasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertFilmUiEvent.deskripsi,
            onValueChange = {onValueChange(insertFilmUiEvent.copy(deskripsi = it))},
            label = { Text("Deskripsin") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertFilmUiEvent.genre,
            onValueChange = {onValueChange(insertFilmUiEvent.copy(genre = it))},
            label = { Text("Genre") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertFilmUiEvent.rating_usia,
            onValueChange = {onValueChange(insertFilmUiEvent.copy(rating_usia = it))},
            label = { Text("Rating Usia") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled){
            Text(
                text = "Data Tidak Boleh Kosong!",
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}