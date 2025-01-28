package com.example.uaspam.ui.view.penayangan

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.model.Penayangan
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiHomePenayangan
import com.example.uaspam.ui.viewmodel.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.penayangan.HomePenayanganUiState
import com.example.uaspam.ui.viewmodel.penayangan.HomePenayanganViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenayanganScreen(
    navigateToPenayanganEntry: () -> Unit,
    navigateToLihatTiket: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    navigateBack: () -> Unit,
    @SuppressLint("NewApi") viewModel: HomePenayanganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePenayangan.titleRes,
                navigateUp = navigateBack,
                canNavigateBack = true,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToPenayanganEntry) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Add Penayangan")
            }
        },
        bottomBar = {
            Button(
                onClick = navigateToLihatTiket,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Lihat Tiket")
            }
        }
    ) { innerPadding ->
        PenayanganStatus(
            penayanganUiState = viewModel.penayanganUIState,
            retryAction = { viewModel.getPenayangan() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePenayangan(it.id_penayangan ?: 0)
                viewModel.getPenayangan()
            }
        )
    }
}

@Composable
fun PenayanganStatus(
    penayanganUiState: HomePenayanganUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Penayangan) -> Unit
) {
    when (penayanganUiState) {
        is HomePenayanganUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomePenayanganUiState.Success ->
            if (penayanganUiState.penayangan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Penayangan")
                }
            } else {
                PenayanganLayout(
                    penayangans = penayanganUiState.penayangan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_penayangan ?: 0) },
                    onDeleteClick = onDeleteClick
                )
            }
        is HomePenayanganUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun PenayanganLayout(
    penayangans: List<Penayangan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Penayangan) -> Unit,
    onDeleteClick: (Penayangan) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(penayangans) { penayangan ->
            PenayanganCard(
                penayangan = penayangan,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(penayangan) },
                onDeleteClick = { onDeleteClick(penayangan) }
            )
        }
    }
}

@Composable
fun PenayanganCard(
    penayangan: Penayangan,
    modifier: Modifier = Modifier,
    onDeleteClick: (Penayangan) -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "ID Film: ${penayangan.id_film}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Studio: ${penayangan.id_studio} ", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Tanggal Penayangan: ${penayangan.tanggal_penayangan}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Harga Tiket: ${penayangan.harga_tiket}", style = MaterialTheme.typography.bodyMedium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                IconButton(onClick = { onDeleteClick(penayangan) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Penayangan")
                }
            }
        }
    }
}