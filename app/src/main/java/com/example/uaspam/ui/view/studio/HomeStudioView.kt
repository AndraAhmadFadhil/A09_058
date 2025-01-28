package com.example.uaspam.ui.view.studio

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.model.Studio
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.viewmodel.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.studio.HomeStudioUiState
import com.example.uaspam.ui.viewmodel.studio.HomeStudioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudioScreen(
    navigateToStudioEntry: () -> Unit,
    navigateToUpdate: (Int) -> Unit,
    navigateBack: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    @SuppressLint("NewApi") viewModel: HomeStudioViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = "Daftar Studio",
                navigateUp = navigateBack,
                canNavigateBack = true,
            )
        },
    ) { innerPadding ->
        StudioStatus(
            studioUiState = viewModel.studioUIState,
            retryAction = { viewModel.getStudio() },
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = {
                viewModel.deleteStudio(it.id_studio ?: 0)
                viewModel.getStudio()
            },
            navigateToStudioEntry = navigateToStudioEntry,
            navigateToUpdate = navigateToUpdate,
            navigateToDetail = navigateToDetail
        )
    }
}

@Composable
fun StudioStatus(
    studioUiState: HomeStudioUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Studio) -> Unit,
    navigateToStudioEntry: () -> Unit,
    navigateToUpdate: (Int) -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    when (studioUiState) {
        is HomeStudioUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeStudioUiState.Success ->
            if (studioUiState.studio.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Tidak ada data Studio")
                    }
                    // Pindahkan tombol ke bagian bawah
                    Button(
                        onClick = navigateToStudioEntry,
                        modifier = Modifier
                            .align(Alignment.BottomCenter) // Mengatur tombol berada di bawah
                            .padding(16.dp)
                    ) {
                        Text("Tambah Studio")
                    }
                }
            } else {
                Column(modifier = modifier.fillMaxSize()) {
                    StudioLayout(
                        studios = studioUiState.studio,
                        modifier = Modifier.weight(1f),
                        onDeleteClick = onDeleteClick,
                        navigateToUpdate = { navigateToUpdate(it.id_studio ?: 0) },
                        navigateToDetail = { navigateToDetail(it.id_studio ?: 0) }
                    )
                    // Tombol pindah ke bawah
                    Button(
                        onClick = navigateToStudioEntry,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally) // Mengatur tombol berada di bawah
                            .padding(16.dp)
                    ) {
                        Text("Tambah Studio")
                    }
                }
            }
        is HomeStudioUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun StudioLayout(
    studios: List<Studio>,
    modifier: Modifier = Modifier,
    onDeleteClick: (Studio) -> Unit,
    navigateToDetail: (Studio) -> Unit,
    navigateToUpdate: (Studio) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(studios) { studio ->
            StudioCard(
                studio = studio,
                onDeleteClick = onDeleteClick,
                onClick = navigateToDetail
            )
        }
    }
}

@Composable
fun StudioCard(
    studio: Studio,
    modifier: Modifier = Modifier,
    onDeleteClick: (Studio) -> Unit,
    onClick: (Studio) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(studio) },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Nama Studio: ${studio.nama_studio}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Kapasitas: ${studio.kapasitas} Orang", style = MaterialTheme.typography.bodyLarge)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onDeleteClick(studio) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Studio")
                }
            }
        }
    }
}
